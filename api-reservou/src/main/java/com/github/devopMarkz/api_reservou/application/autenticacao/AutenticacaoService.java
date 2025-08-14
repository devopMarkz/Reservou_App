package com.github.devopMarkz.api_reservou.application.autenticacao;

import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import com.github.devopMarkz.api_reservou.infraestructure.exception.TokenInvalidoException;
import com.github.devopMarkz.api_reservou.interfaces.dto.login.LoginDTO;
import com.github.devopMarkz.api_reservou.interfaces.dto.token.TokenDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public TokenDTO realizarLogin(LoginDTO loginDTO) {
        var authenticaticationToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.senha());
        var authentication = authenticationManager.authenticate(authenticaticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return montarTokenDTO(usuario);
    }

    public TokenDTO renovarToken(TokenDTO tokenDTO) {
        Usuario usuario = tokenService.validarRefreshToken(tokenDTO.refresh_token());
        String jti = tokenService.obterJtiDoToken(tokenDTO.refresh_token());

        if(!jti.equals(usuario.getRefreshTokenJti())){
            throw new TokenInvalidoException("Refresh token revogado!");
        }

        return montarTokenDTO(usuario);
    }

    private TokenDTO montarTokenDTO(Usuario usuario) {
        String token = tokenService.obterToken(usuario);
        String refreshToken = tokenService.obterRefreshToken(usuario);
        String role = usuario.getPerfil().name();
        String tokenType = tokenService.obterTipoDoToken(token);
        Instant instant = tokenService.obterDataExpiracao(token);

        return new TokenDTO(
                token, refreshToken, role, tokenType, instant
        );
    }

}
