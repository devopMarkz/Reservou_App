package com.github.devopMarkz.api_reservou.domain.model.usuario;

public enum Plano {

    GRATUITO(1),
    BASICO(5),
    PREMIUM(20),
    ILIMITADO(Integer.MAX_VALUE);

    public int valor;

    Plano(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
