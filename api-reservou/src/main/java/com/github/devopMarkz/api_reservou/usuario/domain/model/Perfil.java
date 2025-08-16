package com.github.devopMarkz.api_reservou.usuario.domain.model;

public enum Perfil {

    ROLE_DONO("Dono"),
    ROLE_USUARIO_COMUM("Usuário Comum"),
    ROLE_ADMINISTRADOR("Administrador");

    private String descricao;

    private Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
