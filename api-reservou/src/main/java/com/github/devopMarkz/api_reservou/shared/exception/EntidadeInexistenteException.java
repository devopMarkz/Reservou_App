package com.github.devopMarkz.api_reservou.shared.exception;

public class EntidadeInexistenteException extends RuntimeException {
    public EntidadeInexistenteException(String message) {
        super(message);
    }

    public EntidadeInexistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
