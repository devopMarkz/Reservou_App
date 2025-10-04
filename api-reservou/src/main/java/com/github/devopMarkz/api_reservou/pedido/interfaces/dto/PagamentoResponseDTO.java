package com.github.devopMarkz.api_reservou.pedido.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.devopMarkz.api_reservou.pedido.domain.model.StatusPagamento;
import com.github.devopMarkz.api_reservou.pedido.domain.model.TipoPagamento;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoResponseDTO {

    private Long id;
    private TipoPagamento tipo;
    private StatusPagamento status;
    private BigDecimal valorPago;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataPagamento;

}