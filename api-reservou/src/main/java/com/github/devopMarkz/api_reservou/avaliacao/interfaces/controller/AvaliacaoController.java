package com.github.devopMarkz.api_reservou.avaliacao.interfaces.controller;

import com.github.devopMarkz.api_reservou.avaliacao.application.AvaliacaoService;
import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoRequestDTO;
import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoResponseDTO;
import com.github.devopMarkz.api_reservou.shared.utils.GerenciadorDePermissoes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.devopMarkz.api_reservou.shared.utils.GeradorDeUri.generateUri;

@RestController
@RequestMapping("/estabelecimentos/{idEstabelecimento}/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Void> criarAvaliacao(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @Valid @RequestBody AvaliacaoRequestDTO requestDTO
    ) {
        Long id = avaliacaoService.criarAvaliacao(idEstabelecimento, requestDTO);
        URI location = generateUri(id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idAvaliacao}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Void> atualizarAvaliacao(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @PathVariable("idAvaliacao") Long idAvaliacao,
            @Valid @RequestBody AvaliacaoRequestDTO requestDTO
    ){
        avaliacaoService.atualizarAvaliacao(idEstabelecimento, idAvaliacao, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Page<AvaliacaoResponseDTO>> buscarAvaliacoesPorEstabelecimento(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        Page<AvaliacaoResponseDTO> avaliacoesDTO = avaliacaoService.buscarAvaliacoesPorEstabelecimento(idEstabelecimento, pageNumber, pageSize);
        return ResponseEntity.ok(avaliacoesDTO);
    }

}
