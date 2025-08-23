package com.github.devopMarkz.api_reservou.quadra.infrastructure.mapper;

import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraRequestDTO;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class QuadraMapper {

    @Mapping(source = "estabelecimento.id", target = "idEstabelecimento")
    public abstract QuadraResponseDTO toResponseDTO(Quadra quadra);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "horarios", ignore = true)
    @Mapping(target = "urlFoto", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    public abstract Quadra toEntity(QuadraRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "horarios", ignore = true)
    @Mapping(target = "urlFoto", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    public abstract Quadra toEntityUpdated(
            QuadraRequestDTO requestDTO,
            @MappingTarget Quadra quadra
    );

}
