package com.github.devopMarkz.api_reservou.horario.application;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.service.EstabelecimentoDomainService;
import com.github.devopMarkz.api_reservou.horario.domain.model.DiaSemana;
import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.horario.domain.model.HorarioDia;
import com.github.devopMarkz.api_reservou.horario.domain.repository.HorarioDiaRepository;
import com.github.devopMarkz.api_reservou.horario.domain.repository.HorarioRepository;
import com.github.devopMarkz.api_reservou.horario.domain.repository.specs.HorarioSpecificationsBuilder;
import com.github.devopMarkz.api_reservou.horario.infrastructure.HorarioMapper;
import com.github.devopMarkz.api_reservou.horario.infrastructure.exceptions.HorarioConflituosoException;
import com.github.devopMarkz.api_reservou.horario.interfaces.dto.HorarioRequestDTO;
import com.github.devopMarkz.api_reservou.horario.interfaces.dto.HorarioResponseDTO;
import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.quadra.domain.repository.QuadraRepository;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.usuario.application.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final QuadraRepository quadraRepository;
    private final EstabelecimentoDomainService estabelecimentoDomainService;
    private final HorarioMapper horarioMapper;
    private final HorarioDiaRepository horarioDiaRepository;

    public HorarioService(HorarioRepository horarioRepository,
                          QuadraRepository quadraRepository,
                          EstabelecimentoDomainService estabelecimentoDomainService,
                          HorarioMapper horarioMapper,
                          HorarioDiaRepository horarioDiaRepository) {
        this.horarioRepository = horarioRepository;
        this.quadraRepository = quadraRepository;
        this.estabelecimentoDomainService = estabelecimentoDomainService;
        this.horarioMapper = horarioMapper;
        this.horarioDiaRepository = horarioDiaRepository;
    }

    @Transactional(readOnly = true)
    public HorarioResponseDTO buscarPorId(Long idQuadra, Long idHorario) {
        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new EntidadeInexistenteException("Quadra inexistente."));

        Horario horario = horarioRepository.findByIdAndQuadraId(idHorario, idQuadra)
                .orElseThrow(() -> new EntidadeInexistenteException("Horário não encontrado."));

        return horarioMapper.toHorarioResponseDTO(horario);
    }

    @Transactional(readOnly = true)
    public Page<HorarioResponseDTO> buscarPorFiltros(Long quadraId, Long id, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, BigDecimal preco, Boolean ativo, int pageNumber, int pageSize){
        Quadra quadra = quadraRepository.findById(quadraId)
                .orElseThrow(() -> new EntidadeInexistenteException("Quadra inexistente."));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Horario> horarioSpecification = new HorarioSpecificationsBuilder()
                .withId(id)
                .withQuadraId(quadraId)
                .withDentroDoIntervalo(dataHoraInicio, dataHoraFim)
                .withPreco(preco)
                .withAtivo(ativo)
                .build();

        Page<Horario> horarios = horarioRepository.findAll(horarioSpecification, pageable);

        return horarios.map(horarioMapper::toHorarioResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<HorarioResponseDTO> buscarPorDia(Long quadraId, LocalDate dia, int pageNumber, int pageSize) {
        if (!quadraRepository.existsById(quadraId)) {
            throw new EntidadeInexistenteException("Quadra inexistente.");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        String diaDaSemanaStr = getStringDiaDaSemana(dia);

        Page<Object[]> paginaDeResultadosCrus = horarioRepository.findHorariosParaDia(quadraId, dia, diaDaSemanaStr, pageable);

        Page<HorarioResponseDTO> paginaDeDTOs = paginaDeResultadosCrus.map(row -> {
            LocalDateTime dataHoraInicio = ((java.sql.Timestamp) row[3]).toLocalDateTime();
            LocalDateTime dataHoraFim = ((java.sql.Timestamp) row[4]).toLocalDateTime();

            Double duracaoEmMinutos = ((java.math.BigDecimal) row[6]).doubleValue();

            return new HorarioResponseDTO(
                    (Long) row[0],
                    (Long) row[1],
                    (Long) row[2],
                    dataHoraInicio,
                    dataHoraFim,
                    (BigDecimal) row[5],
                    duracaoEmMinutos
            );
        });

        paginaDeDTOs.forEach(horarioDTO -> {
            List<String> diasDTO = horarioDiaRepository.findAllByHorarioId(horarioDTO.getId())
                    .stream()
                    .map(horarioDia -> horarioDia.getDiaSemana().name())
                    .toList();
            horarioDTO.setDiasDisponivel(diasDTO);
        });

        return paginaDeDTOs;
    }

    @Transactional
    public Long criarHorario(Long idQuadra, HorarioRequestDTO requestDTO) {
        if(!quadraRepository.existsById(idQuadra)) {
            throw new EntidadeInexistenteException("Quadra inexistente.");
        }

        List<Horario> conflitos = horarioRepository.findOverlappingHorarios(
                idQuadra,
                requestDTO.getDataHoraInicio(),
                requestDTO.getDataHoraFim(),
                requestDTO.getDiasDisponivel()
        );

        if (!conflitos.isEmpty()) {
            throw new HorarioConflituosoException("Já existe um horário cadastrado que conflita com os dias e horários informados.");
        }

        Quadra quadra = quadraRepository.getReferenceById(idQuadra);
        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();
        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, quadra.getEstabelecimento());

        Horario horario = horarioMapper.toHorario(requestDTO);
        horario.setQuadra(quadra);
        converterEnumEmDiasDisponiveis(horario, requestDTO.getDiasDisponivel());

        horario = horarioRepository.save(horario);
        return horario.getId();
    }

    @Transactional
    public void atualizarHorario(Long idQuadra, Long idHorario, HorarioRequestDTO requestDTO) {
        if(!quadraRepository.existsById(idQuadra)) {
            throw new EntidadeInexistenteException("Quadra inexistente.");
        }

        Horario horario = horarioRepository.findById(idHorario)
                .orElseThrow(() -> new EntidadeInexistenteException("Horário inexistente."));

        Quadra quadra = quadraRepository.getReferenceById(idQuadra);

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, quadra.getEstabelecimento());

        horarioMapper.toHorarioUpdated(requestDTO, horario);

        horarioRepository.save(horario);
    }

    @Transactional
    public void desativarHorario(Long idQuadra, Long idHorario) {
        if(!quadraRepository.existsById(idQuadra)) {
            throw new EntidadeInexistenteException("Quadra inexistente.");
        }

        Quadra quadra = quadraRepository.getReferenceById(idQuadra);

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, quadra.getEstabelecimento());

        Horario horario = horarioRepository.findById(idHorario)
                .orElseThrow(() -> new EntidadeInexistenteException("Horário inexistente."));

        horario.desativar();
    }

    private void converterEnumEmDiasDisponiveis(Horario horario, Set<DiaSemana> diasSemana){
        Set<HorarioDia> diasDisponiveis = new HashSet<>();

        for (DiaSemana diaSemana : diasSemana){
            HorarioDia horarioDia = new HorarioDia(null, horario, diaSemana);
            diasDisponiveis.add(horarioDia);
        }

        horario.setDiasDisponiveis(diasDisponiveis);
    }

    private String getStringDiaDaSemana(LocalDate dia){
        int dayOfWeek = dia.getDayOfWeek().get(ChronoField.DAY_OF_WEEK);

        String diaDaSemanaStr = null;

        for (DiaSemana diaSemana : DiaSemana.values()){
            if(diaSemana.ordinal() == dayOfWeek){
                return diaSemana.name();
            }
        }

        throw new IllegalStateException("Não foi possível mapear o dia da semana para o Enum: " + dia.getDayOfWeek());
    }

}
