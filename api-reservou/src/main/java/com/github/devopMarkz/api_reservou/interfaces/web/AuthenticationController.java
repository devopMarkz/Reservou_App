package com.github.devopMarkz.api_reservou.interfaces.web;

import com.github.devopMarkz.api_reservou.application.autenticacao.AutenticacaoService;
import com.github.devopMarkz.api_reservou.interfaces.dto.login.LoginDTO;
import com.github.devopMarkz.api_reservou.interfaces.dto.token.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AutenticacaoService autenticacaoService;

    public AuthenticationController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO = autenticacaoService.realizarLogin(loginDTO);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> renovarToken(@RequestBody TokenDTO tokenDTO) {
        TokenDTO tokenRenovado = autenticacaoService.renovarToken(tokenDTO);
        return  ResponseEntity.ok(tokenRenovado);
    }

}
