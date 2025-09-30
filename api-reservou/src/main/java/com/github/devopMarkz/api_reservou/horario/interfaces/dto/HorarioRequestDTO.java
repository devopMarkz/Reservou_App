package com.github.devopMarkz.api_reservou.horario.interfaces.dto;

import com.github.devopMarkz.api_reservou.horario.domain.model.DiaSemana;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class HorarioRequestDTO {

    @NotNull(message = "Data hora de início de horário precisa ser definida.")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "Data hora de término de horário precisa ser definida.")
    private LocalDateTime dataHoraFim;

    @PositiveOrZero(message = "Preço deve ser um número positivo.")
    @NotNull(message = "Preço não pode ser nulo.")
    private BigDecimal preco;

    @NotNull(message = "Horário precisa ter dias de disponibilidade.")
    private Set<DiaSemana> diasDisponivel;

}
