package com.github.devopMarkz.api_reservou.pedido.interfaces.controller;

import com.github.devopMarkz.api_reservou.pedido.application.PedidoService;
import com.github.devopMarkz.api_reservou.pedido.domain.model.TipoPagamento;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.HorarioReservaDTO;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.PedidoResponseDTO;
import com.github.devopMarkz.api_reservou.shared.utils.GerenciadorDePermissoes;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<PedidoResponseDTO> criarPedidoComReservas(
            @RequestBody @Valid List<HorarioReservaDTO> reservasDTO,
            @RequestParam TipoPagamento tipoPagamento
    ) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedidoComReservas(reservasDTO, tipoPagamento);
        return ResponseEntity.ok(pedidoResponseDTO);
    }

}
