package com.github.devopMarkz.api_reservou.usuario.interfaces.dto;

import java.time.Instant;

public record TokenDTO(
        String access_token,
        String refresh_token,
        String role,
        String token_type,
        Instant expires_in
) {
}
