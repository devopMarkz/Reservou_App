package com.github.devopMarkz.api_reservou.quadra.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuadraRequestDTO {

    @NotBlank(message = "Nome da quadra precisa ser informado.")
    private String nome;

    private String tipo;

    private String linkMapaEndereco;

    private String informacoesGerais;

}
