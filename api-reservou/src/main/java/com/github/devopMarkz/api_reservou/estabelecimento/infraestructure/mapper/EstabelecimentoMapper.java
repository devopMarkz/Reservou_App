package com.github.devopMarkz.api_reservou.estabelecimento.infraestructure.mapper;

import com.github.devopMarkz.api_reservou.avaliacao.infraestructure.mapper.AvaliacaoMapper;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto.EstabelecimentoRequestDTO;
import com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto.EstabelecimentoResponseDTO;
import com.github.devopMarkz.api_reservou.quadra.infrastructure.mapper.QuadraMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {QuadraMapper.class, AvaliacaoMapper.class})
public abstract class EstabelecimentoMapper {

    @Mapping(target = "dono", ignore = true)
    public abstract Estabelecimento toEstabelecimento(EstabelecimentoRequestDTO requestDTO);

    public abstract EstabelecimentoResponseDTO toEstabelecimentoResponseDTO(Estabelecimento estabelecimento);

    @Mapping(target = "avaliacoes", ignore = true)
    @Mapping(target = "quadras", ignore = true)
    public abstract EstabelecimentoResponseDTO toEstabelecimentoResponseDTOSemAvaliacoesEQuadras(Estabelecimento estabelecimento);

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

}
