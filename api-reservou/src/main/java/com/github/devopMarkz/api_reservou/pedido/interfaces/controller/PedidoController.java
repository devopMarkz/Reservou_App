package com.github.devopMarkz.api_reservou.pedido.interfaces.controller;

import com.github.devopMarkz.api_reservou.pedido.application.PedidoService;
import com.github.devopMarkz.api_reservou.pedido.domain.model.TipoPagamento;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.HorarioReservaDTO;
import com.github.devopMarkz.api_reservou.pedido.interfaces.dto.PedidoResponseDTO;
import com.github.devopMarkz.api_reservou.shared.utils.GerenciadorDePermissoes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    @GetMapping("/user")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_USUARIO_COMUM)
    public ResponseEntity<Page<PedidoResponseDTO>> buscarPedidosPorUsuario(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ){
        Page<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorUsuario(pageNumber, pageSize);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estabelecimento/{idEstabelecimento}")
    @PreAuthorize(GerenciadorDePermissoes.ROLE_DONO)
    public ResponseEntity<Page<PedidoResponseDTO>> getPedidosByEstabelecimentoAndDono(
            @PathVariable Long idEstabelecimento,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Page<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorDono(idEstabelecimento, pageNumber, pageSize);
        return ResponseEntity.ok(pedidos);
    }

}
