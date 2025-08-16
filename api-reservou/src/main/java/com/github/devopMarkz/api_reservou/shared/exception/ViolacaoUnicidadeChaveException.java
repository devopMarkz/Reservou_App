package com.github.devopMarkz.api_reservou.shared.exception;

public class ViolacaoUnicidadeChaveException extends RuntimeException {
    public ViolacaoUnicidadeChaveException(String message) {
        super(message);
    }

    public ViolacaoUnicidadeChaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
