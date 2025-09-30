package com.github.devopMarkz.api_reservou.horario.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiasDisponivelDTO {

    private Long id;
    private Long idHorario;
    private String diaSemana;

    public DiasDisponivelDTO(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}
