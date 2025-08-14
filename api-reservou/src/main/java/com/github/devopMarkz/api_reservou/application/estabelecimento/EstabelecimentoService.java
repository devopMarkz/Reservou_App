package com.github.devopMarkz.api_reservou.application.estabelecimento;

import com.github.devopMarkz.api_reservou.application.autenticacao.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.application.estabelecimento.mapper.EstabelecimentoMapper;
import com.github.devopMarkz.api_reservou.domain.model.estabelecimento.Estabelecimento;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import com.github.devopMarkz.api_reservou.domain.repository.estabelecimento.EstabelecimentoRepository;
import com.github.devopMarkz.api_reservou.domain.service.estabelecimento.EstabelecimentoDomainService;
import com.github.devopMarkz.api_reservou.infraestructure.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento.EstabelecimentoRequestDTO;
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

        estabelecimentoMapper.updateEstabelecimentoFromDTO(requestDTO, estabelecimento);

        estabelecimentoRepository.save(estabelecimento);
    }

}
