package com.github.devopMarkz.api_reservou.pedido.interfaces.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorarioReservaDTO {

    @NotNull(message = "Necessário informar horário que irá ser reservado.")
    private Long horarioId;

    @NotNull(message = "Necessário informar data da reserva.")
    private LocalDate dia;

    private String privacidadeReserva;
    private Integer limiteParticipantesExternos;
}