package com.github.devopMarkz.api_reservou.interfaces.dto.reserva;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long horarioId;
    private LocalDateTime dataReserva;

}