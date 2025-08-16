package com.github.devopMarkz.api_reservou.usuario.interfaces.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "Nome de usuário precisa ser preenchido.")
    private String nome;

    @NotBlank(message = "E-mail não preenchido.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "Senha de usuário precisa ser preenchida.")
    private String senha;

}
