package com.github.devopMarkz.api_reservou.utils;

public class GerenciadorDeRoles {

    public static final String ROLE_USUARIO_COMUM = "hasAnyRole('ROLE_USUARIO_COMUM', 'ROLE_ADMINISTRADOR')";
    public static final String ROLE_ADMINISTRADOR = "hasRole('ROLE_ADMINISTRADOR')";
    public static final String ROLE_DONO = "hasAnyRole('ROLE_DONO', 'ROLE_ADMINISTRADOR')";

}
