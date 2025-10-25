package com.github.devopMarkz.api_reservou.pedido.interfaces.controller;

import com.github.devopMarkz.api_reservou.pedido.application.ReservaService;
import com.github.devopMarkz.api_reservou.shared.utils.GerenciadorDePermissoes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas/{reservaId}/participantes")
public class ReservaParticipanteController {

    private final ReservaService reservaService;

    public ReservaParticipanteController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/entrar")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Void> entrarNaReserva(@PathVariable Long reservaId) {
        reservaService.entrarNaReserva(reservaId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/sair")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Void> sairDaReserva(@PathVariable Long reservaId) {
        reservaService.sairDaReserva(reservaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Void> removerParticipante(@PathVariable Long reservaId, @PathVariable Long usuarioId) {
        reservaService.removerParticipanteAdmin(reservaId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
