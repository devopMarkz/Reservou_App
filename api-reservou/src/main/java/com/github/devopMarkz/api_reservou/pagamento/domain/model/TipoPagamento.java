package com.github.devopMarkz.api_reservou.pagamento.domain.model;

public enum TipoPagamento {

    PIX("Pix"),
    PRESENCIAL("Presencial");

    private String value;

    TipoPagamento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
