package com.github.devopMarkz.api_reservou.estabelecimento.interfaces.exception;

public class LimiteQuantiaEstabelecimentoException extends RuntimeException {
    public LimiteQuantiaEstabelecimentoException(String message) {
        super(message);
    }

    public LimiteQuantiaEstabelecimentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
