package com.github.devopMarkz.api_reservou.quadra.application;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.repository.EstabelecimentoRepository;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.service.EstabelecimentoDomainService;
import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.quadra.domain.repository.QuadraRepository;
import com.github.devopMarkz.api_reservou.quadra.domain.repository.specs.QuadraSpecificationBuilder;
import com.github.devopMarkz.api_reservou.quadra.domain.service.QuadraDomainService;
import com.github.devopMarkz.api_reservou.quadra.infrastructure.mapper.QuadraMapper;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraRequestDTO;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraResponseDTO;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.usuario.application.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuadraService {

    private final QuadraRepository quadraRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final QuadraMapper quadraMapper;
    private final EstabelecimentoDomainService estabelecimentoDomainService;
    private final QuadraDomainService quadraDomainService;

    public QuadraService(QuadraRepository quadraRepository,
                         EstabelecimentoRepository estabelecimentoRepository,
                         QuadraMapper quadraMapper,
                         EstabelecimentoDomainService estabelecimentoDomainService,
                         QuadraDomainService quadraDomainService) {
        this.quadraRepository = quadraRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.quadraMapper = quadraMapper;
        this.estabelecimentoDomainService = estabelecimentoDomainService;
        this.quadraDomainService = quadraDomainService;
    }

    @Transactional
    public Long criarQuadra(Long idEstabelecimento, QuadraRequestDTO requestDTO){
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente."));

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, estabelecimento);

        Quadra quadra = quadraMapper.toEntity(requestDTO);

        quadraDomainService.criarQuadra(quadra, estabelecimento);

        quadraRepository.save(quadra);

        return quadra.getId();
    }

    @Transactional(readOnly = true)
    public QuadraResponseDTO buscarPorId(Long idEstabelecimento, Long idQuadra) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente."));

        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new EntidadeInexistenteException("Quadra inexistente."));

        quadraDomainService.verificarQuadraEmEstabelecimento(quadra, estabelecimento);

        return quadraMapper.toResponseDTO(quadra);
    }

    @Transactional(readOnly = true)
    public Page<QuadraResponseDTO> buscarPorFiltro(Long idEstabelecimento, String nome, String tipo, Boolean ativo, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Quadra> specs = new QuadraSpecificationBuilder()
                .withIdEstabelecimento(idEstabelecimento)
                .withNome(nome)
                .withTipo(tipo)
                .withAtivo(ativo)
                .build();

        Page<Quadra> quadras = quadraRepository.findAll(specs, pageable);

        return quadras.map(quadraMapper::toResponseDTO);
    }

    @Transactional
    public void atualizarQuadra(Long idEstabelecimento, Long idQuadra, QuadraRequestDTO requestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente."));

        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new EntidadeInexistenteException("Quadra inexistente."));

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, estabelecimento);

        quadraDomainService.verificarQuadraEmEstabelecimento(quadra, estabelecimento);

        quadraMapper.toEntityUpdated(requestDTO, quadra);

        quadraRepository.save(quadra);
    }

    @Transactional
    public void excluirQuadra(Long idEstabelecimento, Long idQuadra) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente."));

        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new EntidadeInexistenteException("Quadra inexistente."));

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, estabelecimento);

        quadraDomainService.verificarQuadraEmEstabelecimento(quadra, estabelecimento);

        quadraRepository.delete(quadra);
    }

    public void desativarQuadra(Long idEstabelecimento, Long idQuadra) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente."));

        Quadra quadra = quadraRepository.findById(idQuadra)
                .orElseThrow(() -> new EntidadeInexistenteException("Quadra inexistente."));

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, estabelecimento);

        quadraDomainService.verificarQuadraEmEstabelecimento(quadra, estabelecimento);

        quadra.setAtivo(false);

        quadraRepository.save(quadra);
    }

//    public void conjuntoDeValidacoesValidaDonoVerificaQuadra(Usuario usuarioLogado, Estabelecimento estabelecimento, Quadra quadra){
//        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, estabelecimento);
//        quadraDomainService.verificarQuadraEmEstabelecimento(quadra, estabelecimento);
//    }
}
