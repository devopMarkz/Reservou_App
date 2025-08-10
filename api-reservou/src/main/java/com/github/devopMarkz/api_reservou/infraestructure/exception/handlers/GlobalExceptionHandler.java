package com.github.devopMarkz.api_reservou.infraestructure.exception.handlers;

import com.github.devopMarkz.api_reservou.infraestructure.exception.TokenInvalidoException;
import com.github.devopMarkz.api_reservou.infraestructure.exception.UsuarioInativoException;
import com.github.devopMarkz.api_reservou.interfaces.dto.erro.ErroDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ErroDTO> handlerTokenInvalido(TokenInvalidoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        List<String> erros = List.of(e.getMessage());
        ErroDTO erroDTO = new ErroDTO(Instant.now().toString(), status.value(), request.getRequestURI(), erros);
        return ResponseEntity.status(status).body(erroDTO);
    }

    @ExceptionHandler(UsuarioInativoException.class)
    public ResponseEntity<ErroDTO> handlerUsuarioInativo(UsuarioInativoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        List<String> erros = List.of(e.getMessage());
        ErroDTO erroDTO = new ErroDTO(Instant.now().toString(), status.value(), request.getRequestURI(), erros);
        return ResponseEntity.status(status).body(erroDTO);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErroDTO> handlerUsernameNotFound(UsernameNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<String> erros = List.of(e.getMessage());
        ErroDTO erroDTO = new ErroDTO(Instant.now().toString(), status.value(), request.getRequestURI(), erros);
        return ResponseEntity.status(status).body(erroDTO);
    }

}
