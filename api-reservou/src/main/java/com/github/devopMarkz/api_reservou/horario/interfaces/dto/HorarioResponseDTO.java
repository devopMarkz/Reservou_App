package com.github.devopMarkz.api_reservou.horario.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorarioResponseDTO {

    private Long id;
    private Long idQuadra;
    private Long idReservaAtual;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private BigDecimal preco;
    private Boolean reservado;
    private Long duracaoEmMinutos;

}
