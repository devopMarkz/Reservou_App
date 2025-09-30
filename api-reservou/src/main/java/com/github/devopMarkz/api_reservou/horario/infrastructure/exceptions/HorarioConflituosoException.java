package com.github.devopMarkz.api_reservou.horario.infrastructure.exceptions;

public class HorarioConflituosoException extends RuntimeException {
    public HorarioConflituosoException(String message) {
        super(message);
    }
}