package com.github.devopMarkz.api_reservou.horario.domain.service;

import com.github.devopMarkz.api_reservou.horario.infrastructure.exceptions.HorarioConflituosoException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HorarioDomainService {

    public void validarHorario(LocalDateTime inicio, LocalDateTime fim) {
        if(inicio.isAfter(fim)) {
            throw new HorarioConflituosoException("Início do horário deve vir antes do fim.");
        }
    }

}
