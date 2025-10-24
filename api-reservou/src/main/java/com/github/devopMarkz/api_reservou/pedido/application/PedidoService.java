package com.github.devopMarkz.api_reservou.pedido.application;

import com.github.devopMarkz.api_reservou.horario.domain.model.DiaSemana;
import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.horario.domain.repository.HorarioRepository;
import com.github.devopMarkz.api_reservou.horario.infrastructure.exceptions.HorarioConflituosoException;
import com.github.devopMarkz.api_reservou.pedido.domain.model.*;
import com.github.devopMarkz.api_reservou.pedido.domain.repository.PagamentoRepository;
import com.github.devopMarkz.api_reservou.pedido.domain.repository.PedidoRepository;
import com.github.devopMarkz.api_reservou.pedido.domain.repository.ReservaRepository;
import com.github.devopMarkz.api_reservou.pedido.infraestructure.exceptions.DataReservaInvalidaException;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.HorarioReservaDTO;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.PagamentoResponseDTO;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.PedidoResponseDTO;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.ReservaResponseDTO;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.usuario.application.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;

@Service
public class PedidoService {

    private final HorarioRepository horarioRepository;
    private final ReservaRepository reservaRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    public PedidoService(HorarioRepository horarioRepository,
                         ReservaRepository reservaRepository,
                         PedidoRepository pedidoRepository,
                         PagamentoRepository pagamentoRepository) {
        this.horarioRepository = horarioRepository;
        this.reservaRepository = reservaRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    public PedidoResponseDTO criarPedidoComReservas(List<HorarioReservaDTO> reservasDTO, TipoPagamento tipoPagamento) {
        if (reservasDTO == null || reservasDTO.isEmpty()) {
            throw new IllegalArgumentException("A lista de reservas não pode estar vazia.");
        }

        Usuario usuario = UsuarioAutenticadoService.getUsuarioAutenticado();

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.CRIADO);
        pedido.setValorTotal(BigDecimal.ZERO);
        pedido.setDataExpiracao(LocalDateTime.now().plusMinutes(30));

        for (HorarioReservaDTO dto : reservasDTO) {
            Horario horario = horarioRepository.findById(dto.getHorarioId())
                    .orElseThrow(() -> new EntidadeInexistenteException("Horário não encontrado: " + dto.getHorarioId()));

            String diaDaSemanaReserva = getStringDiaDaSemana(dto.getDia());

            DiaSemana diaSemana = DiaSemana.valueOf(diaDaSemanaReserva);
            boolean diaDisponivel = horarioRepository.isDiaDisponivel(horario.getId(), diaSemana);
            if (!diaDisponivel) {
                throw new DataReservaInvalidaException(
                        "O horário não está disponível para o dia " + dto.getDia() + " (" + diaDaSemanaReserva + ")");
            }

            // Validando a data da reserva
            LocalDateTime dataLimiteReserva = LocalDateTime.of(
                    dto.getDia().getYear(),
                    dto.getDia().getMonth(),
                    dto.getDia().getDayOfMonth(),
                    horario.getDataHoraFim().getHour(),
                    horario.getDataHoraFim().getMinute()
            );

            LocalDateTime hoje = LocalDateTime.now();

            if(dataLimiteReserva.isBefore(hoje)) {
                throw new DataReservaInvalidaException(
                        "Não foi possível reservar o horário "
                                + horario.getDataHoraInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                                + " - "
                                + horario.getDataHoraFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                                + " pois trata-se de uma data retroativa."
                );
            }

            // Verificando se o horário já foi reservado
            boolean jaReservado = reservaRepository.existsByHorarioAndData(horario.getId(), dto.getDia());
            if (jaReservado) {
                throw new HorarioConflituosoException(
                        "O horário " + horario.getId() + " já está reservado para " + dto.getDia());
            }

            // Criando a reserva
            Reserva reserva = new Reserva();
            reserva.setPedido(pedido);
            reserva.setHorario(horario);
            reserva.setDataReserva(dto.getDia().atTime(horario.getDataHoraInicio().toLocalTime()));
            reserva.setStatus(StatusReserva.PENDENTE_CONFIRMACAO);

            PrivacidadeReserva privacidadeReserva;
            int limiteParticipantesExternos;

            if(dto.getPrivacidadeReserva() == null || dto.getPrivacidadeReserva().isEmpty()) {
                privacidadeReserva = PrivacidadeReserva.PRIVADA;
                limiteParticipantesExternos = 0;
            } else {
                 privacidadeReserva = PrivacidadeReserva.valueOf(dto.getPrivacidadeReserva());
                 if(dto.getLimiteParticipantesExternos() != null) {
                     limiteParticipantesExternos = dto.getLimiteParticipantesExternos();
                 } else {
                     limiteParticipantesExternos = 0;
                 }
            }

            reserva.setPrivacidade(privacidadeReserva);
            reserva.setLimiteParticipantesExternos(limiteParticipantesExternos);

            pedido.getReservas().add(reserva);
            pedido.setValorTotal(pedido.getValorTotal().add(horario.getPreco()));
        }

        // Salva o pedido e as reservas em cascata
        pedido = pedidoRepository.save(pedido);

        // Cria pagamento vinculado ao pedido
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setTipo(tipoPagamento);
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamento.setValorPago(pedido.getValorTotal());

        pagamentoRepository.save(pagamento);

        // Atualiza status do pedido
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        return toPedidoResponseDTO(pedido);
    }

    private PedidoResponseDTO toPedidoResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus().name());
        dto.setValorTotal(pedido.getValorTotal());

        dto.setReservas(pedido.getReservas().stream()
                .map(r -> new ReservaResponseDTO(
                        r.getId(),
                        r.getHorario().getId(),
                        r.getDataReserva(),
                        r.getStatus().name(),
                        r.getPrivacidade().getDescricao(),
                        r.getLimiteParticipantesExternos()
                ))
                .toList()
        );

        if (pedido.getPagamento() != null) {
            PagamentoResponseDTO pagamentoDTO = new PagamentoResponseDTO();
            pagamentoDTO.setId(pedido.getPagamento().getId());
            pagamentoDTO.setTipo(pedido.getPagamento().getTipo());
            pagamentoDTO.setStatus(pedido.getPagamento().getStatus());
            pagamentoDTO.setValorPago(pedido.getPagamento().getValorPago());
            pagamentoDTO.setDataCriacao(pedido.getPagamento().getDataCriacao());
            pagamentoDTO.setDataPagamento(pedido.getPagamento().getDataPagamento());
            dto.setPagamento(pagamentoDTO);
        }

        return dto;
    }

    private String getStringDiaDaSemana(LocalDate dia){
        int dayOfWeek = dia.getDayOfWeek().get(ChronoField.DAY_OF_WEEK);

        for (DiaSemana diaSemana : DiaSemana.values()){
            if(diaSemana.getDia() == dayOfWeek){
                return diaSemana.name();
            }
        }
        throw new IllegalStateException("Não foi possível mapear o dia da semana para o Enum: " + dia.getDayOfWeek());
    }
}
