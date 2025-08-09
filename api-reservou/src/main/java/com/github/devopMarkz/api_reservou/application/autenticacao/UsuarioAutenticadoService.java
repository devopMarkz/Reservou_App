package com.github.devopMarkz.api_reservou.application.autenticacao;

import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAutenticadoService {

    public static Usuario getUsuarioAutenticado() {
        return (Usuario) SecurityContextHolder.
                getContext()
                .getAuthentication()
                .getPrincipal();
    }

}
