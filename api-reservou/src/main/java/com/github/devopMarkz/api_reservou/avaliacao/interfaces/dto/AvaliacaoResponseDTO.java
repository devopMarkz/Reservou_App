package com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto;

import java.time.LocalDateTime;

public class AvaliacaoResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long estabelecimentoId;
    private Double nota;
    private String comentario;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

}