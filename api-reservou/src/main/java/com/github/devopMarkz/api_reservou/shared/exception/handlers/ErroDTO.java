package com.github.devopMarkz.api_reservou.shared.exception.handlers;

import java.util.List;

public record ErroDTO(
        String timestamp,
        Integer status,
        String path,
        List<String> erros
) {
}
