package com.github.devopMarkz.api_reservou.pedido.domain.model;

public enum StatusPagamento {

    PENDENTE("Pendente"),
    PAGO("Pago"),
    EXPIRADO("Expirado"),
    CANCELADO("Cancelado");

    private final String value;

    StatusPagamento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean permiteCancelamento() {
        return PENDENTE.equals(this) || PAGO.equals(this);
    }

    public boolean permitePagamento() {
        return PENDENTE.equals(this);
    }

    public boolean permiteExpirado() {
        return PENDENTE.equals(this);
    }

}
