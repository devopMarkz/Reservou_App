package com.github.devopMarkz.api_reservou.quadra.infrastructure.mapper;

import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraRequestDTO;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class QuadraMapper {

    @Mapping(source = "estabelecimento.id", target = "idEstabelecimento")
    public abstract QuadraResponseDTO toResponseDTO(Quadra quadra);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "horarios", ignore = true)
    public abstract Quadra toEntity(QuadraRequestDTO requestDTO);

}
