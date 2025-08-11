package com.github.devopMarkz.api_reservou.infraestructure.exception;

public class EntidadeInexistenteException extends RuntimeException {
    public EntidadeInexistenteException(String message) {
        super(message);
    }
}
