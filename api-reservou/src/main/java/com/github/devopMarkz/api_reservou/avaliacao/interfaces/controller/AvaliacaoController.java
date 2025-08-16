package com.github.devopMarkz.api_reservou.avaliacao.interfaces.controller;

import com.github.devopMarkz.api_reservou.avaliacao.application.AvaliacaoService;
import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estabelecimentos/{idEstabelecimento}/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public void criarAvaliacao(@Valid @RequestBody AvaliacaoRequestDTO requestDTO) {

    }

}
