package com.github.devopMarkz.api_reservou.shared.exception;

public class ViolacaoRecursoException extends RuntimeException {
    public ViolacaoRecursoException(String message) {
        super(message);
    }

    public ViolacaoRecursoException(String message, Throwable cause) {
        super(message, cause);
    }
}
