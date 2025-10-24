package com.github.devopMarkz.api_reservou.pedido.domain.model;

public enum PrivacidadeReserva {

    PUBLICA("Pública"),
    PRIVADA("Privada");

    private final String descricao;

    PrivacidadeReserva(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
