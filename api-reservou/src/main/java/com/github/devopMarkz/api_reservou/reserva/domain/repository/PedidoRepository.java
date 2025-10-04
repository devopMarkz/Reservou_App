package com.github.devopMarkz.api_reservou.reserva.domain.repository;

import com.github.devopMarkz.api_reservou.reserva.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
