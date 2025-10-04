package com.github.devopMarkz.api_reservou.reserva.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class PagamentoRequestDTO {

    @NotNull(message = "Reserva é obrigatória.")
    private Long reservaId;

    @NotBlank(message = "Tipo de pagamento é obrigatório.")
    private String tipo;

    @NotBlank(message = "Status de pagamento é obrigatório.")
    private String status;

    @NotNull(message = "Valor pago é obrigatório.")
    @PositiveOrZero(message = "Valor pago deve ser maior que 0.")
    private BigDecimal valorPago;

    private String dataPagamento;

}