package com.github.devopMarkz.api_reservou.pedido.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteReservaResponseDTO {

    private Long id;
    private String nome;
    private String email;

}
