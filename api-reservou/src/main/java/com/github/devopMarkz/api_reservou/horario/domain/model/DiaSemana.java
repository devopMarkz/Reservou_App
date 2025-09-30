package com.github.devopMarkz.api_reservou.horario.domain.model;

public enum DiaSemana {

    SEGUNDA(1),
    TERCA(2),
    QUARTA(3),
    QUINTA(4),
    SEXTA(5),
    SABADO(6),
    DOMINGO(7);

    private final int dia;

    DiaSemana(int dia){
        this.dia = dia;
    }

    public int getDia() {
        return dia;
    }
}
