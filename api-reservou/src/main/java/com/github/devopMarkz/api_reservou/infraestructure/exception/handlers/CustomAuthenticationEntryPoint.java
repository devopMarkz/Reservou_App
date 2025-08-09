package com.github.devopMarkz.api_reservou.infraestructure.exception.handlers;

import com.github.devopMarkz.api_reservou.infraestructure.exception.TokenInvalidoException;
import com.github.devopMarkz.api_reservou.infraestructure.exception.UsuarioInativoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable causa = (Throwable) request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

        String mensagem = authException.getMessage();

        if (causa instanceof UsuarioInativoException e) {
            mensagem = e.getMessage();
        } else if(causa instanceof TokenInvalidoException e) {
            mensagem = e.getMessage();
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(mensagem);
    }
}