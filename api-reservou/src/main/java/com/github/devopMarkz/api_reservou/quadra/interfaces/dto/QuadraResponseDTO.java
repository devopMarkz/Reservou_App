package com.github.devopMarkz.api_reservou.quadra.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuadraResponseDTO {

    private Long id;
    private String nome;
    private String tipo;
    private Long idEstabelecimento;
    // private List<HorarioResponseDTO> horarios;

}
