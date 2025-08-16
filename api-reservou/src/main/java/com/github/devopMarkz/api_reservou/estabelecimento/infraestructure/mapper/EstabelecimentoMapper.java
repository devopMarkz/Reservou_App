package com.github.devopMarkz.api_reservou.estabelecimento.infraestructure.mapper;

import com.github.devopMarkz.api_reservou.avaliacao.domain.model.Avaliacao;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.usuario.domain.repository.UsuarioRepository;
import com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto.EstabelecimentoRequestDTO;
import com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto.EstabelecimentoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class EstabelecimentoMapper {

    private UsuarioRepository usuarioRepository;

    @Mapping(target = "dono", ignore = true)
    public abstract Estabelecimento toEstabelecimento(EstabelecimentoRequestDTO requestDTO);

    @Mapping(target = "idDono", expression = "java( toIdDono(estabelecimento) )")
    @Mapping(target = "quadras", expression = "java( toListIdQuadras(estabelecimento) )")
    @Mapping(target = "avaliacoes", expression = "java( toListIdAvaliacoes(estabelecimento) )")
    public abstract EstabelecimentoResponseDTO toEstabelecimentoResponseDTO(Estabelecimento estabelecimento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dono", ignore = true)
    @Mapping(target = "notaMedia", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "quadras", ignore = true)
    @Mapping(target = "avaliacoes", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    public abstract void updateEstabelecimentoFromDTO(
            EstabelecimentoRequestDTO requestDTO,
            @MappingTarget Estabelecimento estabelecimento
    );
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
