package com.github.devopMarkz.api_reservou.interfaces.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String perfil;
    private String plano;
    private Boolean ativo;
    private Set<Long> estabelecimentosIds;

}
