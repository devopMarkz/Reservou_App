package com.github.devopMarkz.api_reservou.horario.interfaces.controller;

import com.github.devopMarkz.api_reservou.horario.application.HorarioService;
import com.github.devopMarkz.api_reservou.horario.interfaces.dto.HorarioRequestDTO;
import com.github.devopMarkz.api_reservou.horario.interfaces.dto.HorarioResponseDTO;
import com.github.devopMarkz.api_reservou.shared.utils.GerenciadorDePermissoes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

import static com.github.devopMarkz.api_reservou.shared.utils.GeradorDeUri.generateUri;

@RestController
@RequestMapping("/quadras/{idQuadra}/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @PostMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> criarHorario(
            @PathVariable Long idQuadra,
            @RequestBody @Valid HorarioRequestDTO requestDTO
    ){
        Long id = horarioService.criarHorario(idQuadra, requestDTO);
        URI location = generateUri(id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<HorarioResponseDTO> buscarPorId(@PathVariable Long idQuadra, @PathVariable Long id){
        return ResponseEntity.ok(horarioService.buscarPorId(idQuadra, id));
    }

    @GetMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Page<HorarioResponseDTO>> buscarPorFiltros(
            @PathVariable Long idQuadra,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "dataHoraInicio", required = false) LocalDateTime dataHoraInicio,
            @RequestParam(name = "dataHoraFim", required = false) LocalDateTime dataHoraFim,
            @RequestParam(name = "preco", required = false) BigDecimal preco,
            @RequestParam(name = "ativo", required = false, defaultValue = "true") Boolean ativo,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize
    ){
        Page<HorarioResponseDTO> horarios = horarioService.buscarPorFiltros(idQuadra, id, dataHoraInicio, dataHoraFim, preco, ativo, pageNumber, pageSize);
        return ResponseEntity.ok(horarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> atualizarHorario(
            @PathVariable Long idQuadra,
            @PathVariable Long id,
            @RequestBody @Valid HorarioRequestDTO requestDTO
    ){
        horarioService.atualizarHorario(idQuadra, id, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> desativarHorario(@PathVariable Long idQuadra, @PathVariable Long id){
        horarioService.desativarHorario(idQuadra, id);
        return ResponseEntity.noContent().build();
    }

}
