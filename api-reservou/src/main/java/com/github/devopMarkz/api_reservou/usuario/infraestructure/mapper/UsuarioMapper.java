package com.github.devopMarkz.api_reservou.usuario.infraestructure.mapper;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import com.github.devopMarkz.api_reservou.usuario.interfaces.dto.UsuarioRequestDTO;
import com.github.devopMarkz.api_reservou.usuario.interfaces.dto.UsuarioResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    public abstract Usuario toUsuario(UsuarioRequestDTO requestDTO);

    @Mapping(target = "estabelecimentosIds", expression = "java( toIdSet(usuario) )")
    public abstract UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);

    protected Set<Long> toIdSet(Usuario usuario) {
        if(usuario.getEstabelecimentos().isEmpty()){
            return Set.of();
        }
        return usuario.getEstabelecimentos()
                .stream()
                .map(Estabelecimento::getId)
                .collect(Collectors.toSet());
    }

}
