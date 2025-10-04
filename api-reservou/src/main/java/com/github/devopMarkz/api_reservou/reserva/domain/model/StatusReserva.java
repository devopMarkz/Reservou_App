package com.github.devopMarkz.api_reservou.reserva.domain.model;

public enum StatusReserva {

    PENDENTE_CONFIRMACAO("Pendente de Confirmação"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada"),
    CONCLUIDA("Concluída");

    private final String descricao;

    StatusReserva(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
