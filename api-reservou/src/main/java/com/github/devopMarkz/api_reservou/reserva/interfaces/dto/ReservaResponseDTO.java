package com.github.devopMarkz.api_reservou.reserva.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.devopMarkz.api_reservou.reserva.domain.model.StatusReserva;
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
    private Long idPedido;
    private Long idHorario;
    private LocalDateTime dataReserva;
    private String status;

}