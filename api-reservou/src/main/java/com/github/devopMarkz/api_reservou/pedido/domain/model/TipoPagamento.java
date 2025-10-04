package com.github.devopMarkz.api_reservou.pedido.domain.model;

public enum TipoPagamento {

    PIX("Pix"),
    PRESENCIAL("Presencial");

    private final String value;

    TipoPagamento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
