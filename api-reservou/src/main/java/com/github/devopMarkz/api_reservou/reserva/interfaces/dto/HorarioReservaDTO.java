package com.github.devopMarkz.api_reservou.reserva.interfaces.dto;

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
    private Long horarioId;
    private LocalDate dia;
}