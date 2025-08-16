package com.github.devopMarkz.api_reservou.estabelecimento.application;

import com.github.devopMarkz.api_reservou.usuario.application.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.estabelecimento.infraestructure.mapper.EstabelecimentoMapper;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.repository.EstabelecimentoRepository;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.repository.specs.EstabelecimentoSpecificationBuilder;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.service.EstabelecimentoDomainService;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto.EstabelecimentoRequestDTO;
import com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto.EstabelecimentoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final EstabelecimentoMapper estabelecimentoMapper;
    private final EstabelecimentoDomainService estabelecimentoDomainService;

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository,
                                  EstabelecimentoMapper estabelecimentoMapper,
                                  EstabelecimentoDomainService estabelecimentoDomainService) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.estabelecimentoMapper = estabelecimentoMapper;
        this.estabelecimentoDomainService = estabelecimentoDomainService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long criarEstabelecimento(EstabelecimentoRequestDTO requestDTO) {
        // Obtém o usuário logado a partir do contexto de segurança da aplicação
        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        // Validação de regra de negócio de limite de estabelecimentos criados
        estabelecimentoDomainService.validarLimiteEstabelecimentos(usuarioLogado);

        // Mapeamento de DTO pra entidade
        Estabelecimento estabelecimento = estabelecimentoMapper.toEstabelecimento(requestDTO);

        // Configura o dono do estabelecimento
        estabelecimentoDomainService.criarEstabelecimento(usuarioLogado, estabelecimento);

        estabelecimentoRepository.save(estabelecimento);

        return estabelecimento.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void atualizarEstabelecimento(Long id, EstabelecimentoRequestDTO requestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente!"));

        // Obtém o usuário logado a partir do contexto de segurança da aplicação
        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        // Valida se o dono do estabelecimento é o mesmo usuário logado no contexto de segurança da aplicação
        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, estabelecimento);

        // Atualiza estabelecimento com base no DTO recebido
        estabelecimentoMapper.updateEstabelecimentoFromDTO(requestDTO, estabelecimento);

        estabelecimentoRepository.save(estabelecimento);
    }

    @Transactional(readOnly = true)
    public EstabelecimentoResponseDTO buscarPorId(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente!"));

        return estabelecimentoMapper.toEstabelecimentoResponseDTO(estabelecimento);
    }

    @Transactional(readOnly = true)
    public Page<EstabelecimentoResponseDTO> buscarEstabelecimentosParaDono(String nome, Boolean ativo, String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep, int pageNumber, int pageSize) {
        Usuario usuarioDonoLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Estabelecimento> spec = new EstabelecimentoSpecificationBuilder()
                .withDono(usuarioDonoLogado.getId())
                .withNome(nome)
                .withAtivo(ativo)
                .withLogradouro(logradouro)
                .withNumero(numero)
                .withComplemento(complemento)
                .withBairro(bairro)
                .withCidade(cidade)
                .withEstado(estado)
                .withCep(cep)
                .build();

        Page<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll(spec, pageable);

        return estabelecimentos.map(estabelecimentoMapper::toEstabelecimentoResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<EstabelecimentoResponseDTO> buscarEstabelecimentosParaUsuarios(String nome, Boolean ativo, String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Specification<Estabelecimento> spec = new EstabelecimentoSpecificationBuilder()
                .withNome(nome)
                .withAtivo(ativo)
                .withLogradouro(logradouro)
                .withNumero(numero)
                .withComplemento(complemento)
                .withBairro(bairro)
                .withCidade(cidade)
                .withEstado(estado)
                .withCep(cep)
                .build();

        Page<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll(spec, pageable);

        return estabelecimentos.map(estabelecimentoMapper::toEstabelecimentoResponseDTO);
    }

    @Transactional
    public void desativarEstabelecimento(Long id) {
        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente"));

        estabelecimentoDomainService.validarDonoEstabelecimento(usuarioLogado, estabelecimento);

        estabelecimento.desativar();

        estabelecimentoRepository.save(estabelecimento);
    }

}
