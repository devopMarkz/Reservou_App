package com.github.devopMarkz.api_reservou.pedido.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.devopMarkz.api_reservou.pedido.domain.model.PrivacidadeReserva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservaResponseDTO {

    private Long id;
    private Long horarioId;
    private LocalDateTime dataReserva;
    private String status;
    private String privacidade;
    private Integer limiteParticipantesExternos;

}