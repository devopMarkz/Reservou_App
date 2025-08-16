package com.github.devopMarkz.api_reservou.pagamento.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagamentoResponseDTO {

    private Long id;
    private Long reservaId;
    private String tipo;
    private String status;
    private BigDecimal valorPago;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataPagamento;

}