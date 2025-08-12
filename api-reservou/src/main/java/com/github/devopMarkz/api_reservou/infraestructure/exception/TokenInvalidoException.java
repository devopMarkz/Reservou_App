package com.github.devopMarkz.api_reservou.infraestructure.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenInvalidoException extends AuthenticationException {
    public TokenInvalidoException(String message) {
        super(message);
    }

    public TokenInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
