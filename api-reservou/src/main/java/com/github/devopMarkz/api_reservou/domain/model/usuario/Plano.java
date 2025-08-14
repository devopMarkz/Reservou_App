package com.github.devopMarkz.api_reservou.domain.model.usuario;

public enum Plano {

    GRATUITO(1),
    BASICO(5),
    PREMIUM(20),
    ILIMITADO(Integer.MAX_VALUE);

    public final int quantidadeEstabelecimentos;

    Plano(int quantidadeEstabelecimentos) {
        this.quantidadeEstabelecimentos = quantidadeEstabelecimentos;
    }

    public int getQuantidadeEstabelecimentos() {
        return quantidadeEstabelecimentos;
    }
}
