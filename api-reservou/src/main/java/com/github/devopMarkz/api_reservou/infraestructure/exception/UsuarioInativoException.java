package com.github.devopMarkz.api_reservou.infraestructure.exception;

import org.springframework.security.core.AuthenticationException;

public class UsuarioInativoException extends AuthenticationException {
    public UsuarioInativoException(String mensagem) {
        super(mensagem);
    }
}