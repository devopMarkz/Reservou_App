package com.github.devopMarkz.api_reservou.domain.model.pagamento;

import com.github.devopMarkz.api_reservou.domain.model.reserva.Reserva;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "tb_pagamentos")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    public Pagamento() {
    }

    public Pagamento(Reserva reserva, TipoPagamento tipo, StatusPagamento status, BigDecimal valorPago, LocalDateTime dataPagamento) {
        this.reserva = reserva;
        this.tipo = tipo;
        this.status = status;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
    }

    public void marcarComoPago() {
        if (status.permitePagamento()) {
            this.status = StatusPagamento.PAGO;
            this.dataPagamento = LocalDateTime.now();
        } else {
            throw new IllegalStateException("O pagamento não pode ser marcado como pago no status atual.");
        }
    }

    public void cancelar() {
        if (status.permiteCancelamento()) {
            this.status = StatusPagamento.CANCELADO;
        } else {
            throw new IllegalStateException("O pagamento não pode ser cancelado no status atual.");
        }
    }

    public void marcarComoExpirado() {
        if (status.permiteExpirado()) {
            this.status = StatusPagamento.EXPIRADO;
        } else {
            throw new IllegalStateException("O pagamento não pode ser marcado como expirado no status atual.");
        }
    }
}
