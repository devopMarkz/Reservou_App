package com.github.devopMarkz.api_reservou.infraestructure.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}
