package com.github.devopMarkz.api_reservou.avaliacao.infraestructure.mapper;

import com.github.devopMarkz.api_reservou.avaliacao.domain.model.Avaliacao;
import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoRequestDTO;
import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AvaliacaoMapper {

    @Mapping(source = "usuario.nome", target = "nomeUsuario")
    @Mapping(source = "estabelecimento.id", target = "idEstabelecimento")
    public abstract AvaliacaoResponseDTO toResponseDTO(Avaliacao avaliacao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    public abstract Avaliacao toEntity(AvaliacaoRequestDTO requestDTO);

}
