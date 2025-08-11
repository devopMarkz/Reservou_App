package com.github.devopMarkz.api_reservou.infraestructure.security;

import com.github.devopMarkz.api_reservou.infraestructure.exception.TokenInvalidoException;
import com.github.devopMarkz.api_reservou.infraestructure.exception.handlers.CustomAuthenticationEntryPoint;
import com.github.devopMarkz.api_reservou.infraestructure.exception.UsuarioInativoException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public CustomAuthenticationFilter(TokenService tokenService,
                                      CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.tokenService = tokenService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    /**
     * Metodo executado uma vez por requisição. Verifica a presença do token JWT no cabeçalho,
     * valida o token e autentica o usuário no contexto do Spring Security.
     *
     * @param request     a requisição HTTP.
     * @param response    a resposta HTTP.
     * @param filterChain cadeia de filtros que será continuada após este filtro.
     * @throws ServletException em caso de falha no processamento da requisição.
     * @throws IOException em caso de erro de I/O.
     * @throws UsuarioInativoException em caso de usuário inativo.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        if (request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/auth/refresh")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        try {
            String token = extraiTokenDoHeader(request);

            if (token != null) {
                var usuario = tokenService.validarToken(token);

                if (usuario.getAtivo()) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new UsuarioInativoException("Usuário inativo");
                }
            }
        } catch (UsuarioInativoException | TokenInvalidoException e) {
            customAuthenticationEntryPoint.commence(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token JWT do cabeçalho "Authorization" da requisição HTTP.
     *
     * @param request a requisição HTTP.
     * @return o token sem o prefixo "Bearer ", ou {@code null} se o cabeçalho estiver ausente ou mal formatado.
     */
    private String extraiTokenDoHeader(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        if(!authHeader.split(" ")[0].equals("Bearer")) return null;
        return authHeader.split(" ")[1];
    }

}
