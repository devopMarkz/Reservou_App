package com.github.devopMarkz.api_reservou.reserva.interfaces.controller;

import com.github.devopMarkz.api_reservou.reserva.application.ReservaService;
import com.github.devopMarkz.api_reservou.reserva.domain.model.TipoPagamento;
import com.github.devopMarkz.api_reservou.reserva.interfaces.dto.HorarioReservaDTO;
import com.github.devopMarkz.api_reservou.reserva.interfaces.dto.PedidoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedidoComReservas(
            @RequestBody @Valid List<HorarioReservaDTO> reservasDTO,
            @RequestParam TipoPagamento tipoPagamento
    ) {
        // Chama o serviço para criar o pedido com as reservas
        PedidoResponseDTO pedidoResponseDTO = reservaService.criarPedidoComReservas(reservasDTO, tipoPagamento);

        // Retorna a resposta com o pedido criado
        return ResponseEntity.ok(pedidoResponseDTO);
    }

}
