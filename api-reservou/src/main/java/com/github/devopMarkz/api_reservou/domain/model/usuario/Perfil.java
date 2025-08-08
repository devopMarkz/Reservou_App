package com.github.devopMarkz.api_reservou.domain.model.usuario;

public enum Perfil {

    DONO("Dono"),
    USUARIO_COMUM("Usuário Comum"),
    ADMINISTRADOR("Administrador");

    private String descricao;

    private Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
