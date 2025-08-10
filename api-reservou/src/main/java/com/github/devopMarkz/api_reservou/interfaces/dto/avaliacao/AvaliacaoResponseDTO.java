package com.github.devopMarkz.api_reservou.interfaces.dto.avaliacao;

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