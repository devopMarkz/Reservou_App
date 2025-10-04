package com.github.devopMarkz.api_reservou.reserva.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResponseDTO {

    private Long id;
    private Long usuarioId;
    private LocalDateTime dataPedido;
    private String status;
    private BigDecimal valorTotal;
    private List<ReservaResponseDTO> reservas;
    private PagamentoResponseDTO pagamento;

}