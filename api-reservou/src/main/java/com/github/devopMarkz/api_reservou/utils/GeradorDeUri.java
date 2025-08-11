package com.github.devopMarkz.api_reservou.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GeradorDeUri {

    static <T> URI generateUri(T id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
