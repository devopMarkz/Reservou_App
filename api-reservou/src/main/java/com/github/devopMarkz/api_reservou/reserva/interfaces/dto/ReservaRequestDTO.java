package com.github.devopMarkz.api_reservou.reserva.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotNull(message = "ID do horário deve ser informado.")
    private Long idHorario;

    @NotBlank(message = "Data da reserva precisa ser informada.")
    private String dataReserva;

}
