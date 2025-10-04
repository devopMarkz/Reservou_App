package com.github.devopMarkz.api_reservou.pedido.domain.model;

public enum StatusPedido {

    CRIADO("Criado"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGO("Pago"),
    CANCELADO("Cancelado"),
    REEMBOLSADO("Reembolsado");

    private String descricao;

    private StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
