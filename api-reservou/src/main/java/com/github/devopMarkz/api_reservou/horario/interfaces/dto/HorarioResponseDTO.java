package com.github.devopMarkz.api_reservou.horario.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class HorarioResponseDTO {

    private Long id;
    private Long idQuadra;
    private Long idReserva;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private BigDecimal preco;
    private Double duracaoEmMinutos;

    private List<String> diasDisponivel;

    public HorarioResponseDTO(Long id, Long idQuadra, Long idReserva, LocalDateTime dataHoraInicio,
                              LocalDateTime dataHoraFim, BigDecimal preco, Double duracaoEmMinutos) {
        this.id = id;
        this.idQuadra = idQuadra;
        this.idReserva = idReserva;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

}
