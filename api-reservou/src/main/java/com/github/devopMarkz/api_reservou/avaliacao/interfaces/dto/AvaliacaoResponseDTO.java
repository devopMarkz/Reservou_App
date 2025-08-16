package com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoResponseDTO {

    private Long id;
    private String nomeUsuario;
    private Long idEstabelecimento;
    private Double nota;
    private String comentario;
    private LocalDateTime dataCriacao;
    // private LocalDateTime dataAtualizacao;

}