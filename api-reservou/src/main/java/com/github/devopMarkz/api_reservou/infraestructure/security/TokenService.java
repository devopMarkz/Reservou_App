package com.github.devopMarkz.api_reservou.infraestructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import com.github.devopMarkz.api_reservou.domain.repository.usuario.UsuarioRepository;
import com.github.devopMarkz.api_reservou.infraestructure.exception.TokenInvalidoException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class TokenService {

    private final UsuarioRepository usuarioRepository;

    @Value("${token.secret.signature}")
    private String secret;

    @Value("${token.expiration-time}")
    private Long tempoExpiracaoToken;

    @Value("${token.refresh-expiration-time}")
    private Long tempoExpiracaoRefreshToken;

    private Algorithm algorithm;

    private final String ISSUER = "api-reservou";

    public TokenService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String obterToken(Usuario usuario) {
        return gerarToken(usuario);
    }

    public String obterRefreshToken(Usuario usuario) {
        return gerarRefreshToken(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario validarToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        try {
            String email = verifier.verify(token).getSubject();

            System.out.println(usuarioRepository.existsByEmail(email));

            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
        } catch (JWTCreationException | TokenExpiredException exception) {
            throw new TokenInvalidoException("Token expirado ou inválido");
        }
    }

    @Transactional(readOnly = true)
    public Usuario validarRefreshToken(String refreshToken) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        try {
            DecodedJWT decodedJWT = verifier.verify(refreshToken);

            String jti = decodedJWT.getId();
            String email = decodedJWT.getSubject();

            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

            if (usuario.getRefreshTokenJti() == null) {
                usuario.setRefreshTokenJti(jti);
                usuarioRepository.save(usuario);
            } else {
                if (!jti.equals(usuario.getRefreshTokenJti())) {
                    throw new TokenInvalidoException("Refresh token revogado!");
                }
            }

            return usuario;

        } catch (JWTVerificationException exception) {
            throw new TokenInvalidoException("Refresh token expirado ou inválido");
        }
    }

    public Instant obterDataExpiracao(String token) {
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getExpiresAtAsInstant();
    }

    public String obterTipoDoToken(String token) {
        return "Bearer";
    }

    public String obterJtiDoToken(String token) {
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getId();
    }

    private String gerarToken(Usuario usuario) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail())
                    .withClaim("roles", usuario.getPerfil().name())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("ativo", usuario.getAtivo())
                    .withClaim("id", usuario.getId())
                    .withJWTId(UUID.randomUUID().toString())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(expiration())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new TokenInvalidoException("Erro ao criar o token JWT");
        }
    }

    private String gerarRefreshToken(Usuario usuario) {
        try {
            String jti = UUID.randomUUID().toString();

            usuario.setRefreshTokenJti(jti);
            usuarioRepository.save(usuario);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail())
                    .withJWTId(jti)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(refreshExpiration())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new TokenInvalidoException("Erro ao criar o Refresh Token");
        }
    }

    private Instant expiration() {
        return Instant.now()
                .plus(tempoExpiracaoToken, ChronoUnit.HOURS)
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }

    private Instant refreshExpiration() {
        return Instant.now()
                .plus(tempoExpiracaoRefreshToken, ChronoUnit.HOURS)
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }

}
