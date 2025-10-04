package com.github.devopMarkz.api_reservou.pedido.domain.repository;

import com.github.devopMarkz.api_reservou.pedido.domain.model.Pedido;
import com.github.devopMarkz.api_reservou.pedido.domain.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatusAndDataExpiracaoBefore(StatusPedido status, LocalDateTime dataExpiracaoBefore);
}
