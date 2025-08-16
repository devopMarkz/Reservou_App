package com.github.devopMarkz.api_reservou.horario.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class HorarioRequestDTO {

    @NotNull(message = "ID da quadra deve ser informado.")
    private Long idQuadra;

    @NotBlank(message = "Data Hora de início deve ser informada.")
    private String dataHoraInicio;

    @NotBlank(message = "Data Hora de término deve ser informada.")
    private String dataHoraFim;

    @PositiveOrZero(message = "Preço deve ser um número positivo.")
    @NotNull(message = "Preço não pode ser nulo.")
    private BigDecimal preco;

}
