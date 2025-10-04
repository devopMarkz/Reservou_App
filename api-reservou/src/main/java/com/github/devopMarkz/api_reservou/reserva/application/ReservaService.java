package com.github.devopMarkz.api_reservou.reserva.application;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.horario.domain.repository.HorarioRepository;
import com.github.devopMarkz.api_reservou.horario.infrastructure.exceptions.HorarioConflituosoException;
import com.github.devopMarkz.api_reservou.reserva.domain.model.*;
import com.github.devopMarkz.api_reservou.reserva.domain.model.Pagamento;
import com.github.devopMarkz.api_reservou.reserva.domain.model.Pedido;
import com.github.devopMarkz.api_reservou.reserva.domain.repository.PagamentoRepository;
import com.github.devopMarkz.api_reservou.reserva.domain.repository.PedidoRepository;
import com.github.devopMarkz.api_reservou.reserva.domain.repository.ReservaRepository;
import com.github.devopMarkz.api_reservou.reserva.infraestructure.exceptions.DataReservaInvalidaException;
import com.github.devopMarkz.api_reservou.reserva.interfaces.dto.HorarioReservaDTO;
import com.github.devopMarkz.api_reservou.reserva.interfaces.dto.PagamentoResponseDTO;
import com.github.devopMarkz.api_reservou.reserva.interfaces.dto.PedidoResponseDTO;
import com.github.devopMarkz.api_reservou.reserva.interfaces.dto.ReservaResponseDTO;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.usuario.application.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservaService {

    private final HorarioRepository horarioRepository;
    private final ReservaRepository reservaRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    public ReservaService(HorarioRepository horarioRepository,
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

        for (HorarioReservaDTO dto : reservasDTO) {
            Horario horario = horarioRepository.findById(dto.getHorarioId())
                    .orElseThrow(() -> new EntidadeInexistenteException("Horário não encontrado: " + dto.getHorarioId()));

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

            pedido.getReservas().add(reserva);
            pedido.setValorTotal(pedido.getValorTotal().add(horario.getPreco()));
        }

        // Salva o pedido e as reservas em cascata
        pedido = pedidoRepository.save(pedido);

        // Cria pagamento vinculado ao pedido
        com.github.devopMarkz.api_reservou.reserva.domain.model.Pagamento pagamento = new Pagamento();
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

        // Convertendo Reservas para DTO
        dto.setReservas(pedido.getReservas().stream()
                .map(r -> new ReservaResponseDTO(
                        r.getId(),
                        r.getHorario().getId(),
                        r.getDataReserva(),
                        r.getStatus().name()
                ))
                .toList()
        );

        // Pagamento (se existir)
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
}
