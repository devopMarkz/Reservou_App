package com.github.devopMarkz.api_reservou.application.estabelecimento.mapper;

import com.github.devopMarkz.api_reservou.domain.model.avaliacao.Avaliacao;
import com.github.devopMarkz.api_reservou.domain.model.estabelecimento.Estabelecimento;
import com.github.devopMarkz.api_reservou.domain.model.quadra.Quadra;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import com.github.devopMarkz.api_reservou.domain.repository.usuario.UsuarioRepository;
import com.github.devopMarkz.api_reservou.infraestructure.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento.EstabelecimentoRequestDTO;
import com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento.EstabelecimentoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class EstabelecimentoMapper {

    private UsuarioRepository usuarioRepository;

    @Mapping(target = "dono", expression = "java( toUsuario(requestDTO) )")
    public abstract Estabelecimento toEstabelecimento(EstabelecimentoRequestDTO requestDTO);

    @Mapping(target = "idDono", expression = "java( toIdDono(estabelecimento) )")
    @Mapping(target = "quadras", expression = "java( toListIdQuadras(estabelecimento) )")
    @Mapping(target = "avaliacoes", expression = "java( toListIdAvaliacoes(estabelecimento) )")
    public abstract EstabelecimentoResponseDTO toEstabelecimentoResponseDTO(Estabelecimento estabelecimento);

    protected Usuario toUsuario(EstabelecimentoRequestDTO requestDTO) {
        return usuarioRepository.findById(requestDTO.getIdDono())
                .orElseThrow(() -> new EntidadeInexistenteException("Usuário não encontrado."));
    }

    protected Long toIdDono(Estabelecimento estabelecimento) {
        return estabelecimento.getDono().getId();
    }

    protected List<Long> toListIdQuadras(Estabelecimento estabelecimento) {
        return estabelecimento.getQuadras().stream().map(Quadra::getId).collect(Collectors.toList());
    }

    protected List<Long> toListIdAvaliacoes(Estabelecimento estabelecimento) {
        return estabelecimento.getAvaliacoes().stream().map(Avaliacao::getId).collect(Collectors.toList());
    }

}
