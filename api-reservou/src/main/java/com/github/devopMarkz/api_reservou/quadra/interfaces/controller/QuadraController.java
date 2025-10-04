package com.github.devopMarkz.api_reservou.quadra.interfaces.controller;

import com.github.devopMarkz.api_reservou.quadra.application.QuadraService;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraRequestDTO;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraResponseDTO;
import com.github.devopMarkz.api_reservou.shared.utils.GerenciadorDePermissoes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.github.devopMarkz.api_reservou.shared.utils.GeradorDeUri.generateUri;

@RestController
@RequestMapping("/estabelecimentos/{idEstabelecimento}/quadras")
public class QuadraController {

    private final QuadraService quadraService;

    public QuadraController(QuadraService quadraService){
        this.quadraService = quadraService;
    }

    @PostMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> criarQuadra(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @Valid @RequestBody QuadraRequestDTO requestDTO
    ){
        Long idQuadra = quadraService.criarQuadra(idEstabelecimento, requestDTO);
        URI location = generateUri(idQuadra);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{idQuadra}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<QuadraResponseDTO> buscarPorId(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @PathVariable("idQuadra") Long idQuadra
    ){
        QuadraResponseDTO responseDTO = quadraService.buscarPorId(idEstabelecimento, idQuadra);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Page<QuadraResponseDTO>> buscarPorFiltros(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "tipo", required = false)  String tipo,
            @RequestParam(name = "ativo", required = false) Boolean ativo,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
            ){
        Page<QuadraResponseDTO> quadrasDTOs = quadraService.buscarPorFiltro(idEstabelecimento, nome, tipo, ativo, pageNumber, pageSize);
        return ResponseEntity.ok(quadrasDTOs);
    }

    @PutMapping("/{idQuadra}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> atualizarQuadra(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @PathVariable("idQuadra") Long idQuadra,
            @RequestBody QuadraRequestDTO requestDTO
    ){
        quadraService.atualizarQuadra(idEstabelecimento, idQuadra, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idQuadra}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> excluirQuadra(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @PathVariable("idQuadra") Long idQuadra
    ){
        quadraService.excluirQuadra(idEstabelecimento, idQuadra);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idQuadra}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> desativarQuadra(
            @PathVariable("idEstabelecimento") Long idEstabelecimento,
            @PathVariable("idQuadra") Long idQuadra
    ){
        quadraService.desativarQuadra(idEstabelecimento, idQuadra);
        return ResponseEntity.noContent().build();
    }

}
