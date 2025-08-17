package com.github.devopMarkz.api_reservou.avaliacao.application;

import com.github.devopMarkz.api_reservou.avaliacao.domain.AvaliacaoDomainService;
import com.github.devopMarkz.api_reservou.avaliacao.domain.model.Avaliacao;
import com.github.devopMarkz.api_reservou.avaliacao.domain.repository.AvaliacaoRepository;
import com.github.devopMarkz.api_reservou.avaliacao.infraestructure.mapper.AvaliacaoMapper;
import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoRequestDTO;
import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoResponseDTO;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.repository.EstabelecimentoRepository;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.shared.exception.ViolacaoUnicidadeChaveException;
import com.github.devopMarkz.api_reservou.usuario.application.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final AvaliacaoMapper avaliacaoMapper;
    private final AvaliacaoDomainService avaliacaoDomainService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            AvaliacaoMapper avaliacaoMapper,
                            EstabelecimentoRepository estabelecimentoRepository,
                            AvaliacaoDomainService avaliacaoDomainService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoMapper = avaliacaoMapper;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.avaliacaoDomainService = avaliacaoDomainService;
    }

    @Transactional
    public Long criarAvaliacao(Long idEstabelecimento, AvaliacaoRequestDTO requestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente."));

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        if(avaliacaoRepository.existsByIdUsuarioAndIdEstabelecimento(estabelecimento.getId(), usuarioLogado.getId())) {
            throw new ViolacaoUnicidadeChaveException("Usuário " + usuarioLogado.getId() + " já fez uma avaliação sobre o estabelecimento " + estabelecimento.getNome() + ".");
        }

        Avaliacao avaliacao = avaliacaoMapper.toEntity(requestDTO);

        avaliacaoDomainService.criarAvaliacao(usuarioLogado, avaliacao, estabelecimento);

        avaliacaoRepository.save(avaliacao);

        return avaliacao.getId();
    }

    @Transactional
    public void atualizarAvaliacao(Long idEstabelecimento, Long idAvaliacao, AvaliacaoRequestDTO requestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new EntidadeInexistenteException("Estabelecimento inexistente."));

        Avaliacao avaliacao = avaliacaoRepository.findById(idAvaliacao)
                .orElseThrow(() -> new EntidadeInexistenteException("Avaliação inexistente."));

        avaliacaoDomainService.validarSeAvaliacaoPertenceAoEstabelecimento(estabelecimento, avaliacao);

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        avaliacaoDomainService.validarDonoAvaliacao(usuarioLogado, avaliacao);

        avaliacaoMapper.updateAvaliacaoFromDTO(requestDTO, avaliacao);

        avaliacaoRepository.save(avaliacao);
    }

    @Transactional(readOnly = true)
    public Page<AvaliacaoResponseDTO> buscarAvaliacoesPorEstabelecimento(Long idEstabelecimento, int pageNumber, int pageSize) {
        Pageable pageable  = PageRequest.of(pageNumber, pageSize);
        Page<Avaliacao> avaliacoes = avaliacaoRepository.findByEstabelecimentoId(idEstabelecimento, pageable);
        return avaliacoes.map(avaliacaoMapper::toResponseDTO);
    }


}
