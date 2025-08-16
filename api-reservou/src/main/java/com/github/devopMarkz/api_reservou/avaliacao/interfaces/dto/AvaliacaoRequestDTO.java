package com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AvaliacaoRequestDTO {

//    @NotNull(message = "Estabelecimento é obrigatório")
//    private Long idEstabelecimento;

    @NotNull(message = "Nota é obrigatória")
    private Double nota;

    private String comentario;

}