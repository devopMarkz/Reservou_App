package com.github.devopMarkz.api_reservou.horario.application;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.service.EstabelecimentoDomainService;
import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.horario.domain.repository.HorarioRepository;
import com.github.devopMarkz.api_reservou.horario.domain.repository.specs.HorarioSpecificationsBuilder;
import com.github.devopMarkz.api_reservou.horario.infrastructure.HorarioMapper;
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
import java.time.LocalDateTime;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final QuadraRepository quadraRepository;
    private final EstabelecimentoDomainService estabelecimentoDomainService;
    private final HorarioMapper horarioMapper;

    public HorarioService(HorarioRepository horarioRepository,
                          QuadraRepository quadraRepository,
                          EstabelecimentoDomainService estabelecimentoDomainService,
                          HorarioMapper horarioMapper) {
        this.horarioRepository = horarioRepository;
        this.quadraRepository = quadraRepository;
        this.estabelecimentoDomainService = estabelecimentoDomainService;
        this.horarioMapper = horarioMapper;
    }

    @Transactional(readOnly = true)
    public HorarioResponseDTO buscarPorId(Long idQuadra, Long idHorario) {
        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new EntidadeInexistenteException("Quadra inexistente."));

        Horario horario = horarioRepository.findById(idHorario)
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

    @Transactional
    public Long criarHorario(Long idQuadra, HorarioRequestDTO requestDTO) {
        if(!quadraRepository.existsById(idQuadra)) {
            throw new EntidadeInexistenteException("Quadra inexistente.");
        }

        Quadra quadra = quadraRepository.getReferenceById(idQuadra);

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, quadra.getEstabelecimento());

        Horario horario = horarioMapper.toHorario(requestDTO);

        horario.setQuadra(quadra);

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

}
