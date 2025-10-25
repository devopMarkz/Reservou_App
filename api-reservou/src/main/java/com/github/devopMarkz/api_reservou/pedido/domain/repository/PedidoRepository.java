package com.github.devopMarkz.api_reservou.pedido.domain.repository;

import com.github.devopMarkz.api_reservou.pedido.domain.model.Pedido;
import com.github.devopMarkz.api_reservou.pedido.domain.model.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatusAndDataExpiracaoBefore(StatusPedido status, LocalDateTime dataExpiracaoBefore);

    @EntityGraph(attributePaths = {"reservas", "reservas.participantes"})
    @Query("SELECT p FROM Pedido p WHERE p.usuario.id = :idUsuario")
    Page<Pedido> buscarPedidosPorIdUsuario(Long idUsuario, Pageable pageable);

    @Query("SELECT p FROM Pedido p " +
            "JOIN p.reservas r " +
            "JOIN r.horario h " +
            "JOIN h.quadra q " +
            "JOIN q.estabelecimento e " +
            "WHERE e.id = :idEstabelecimento " +
            "AND e.dono.id = :idUsuarioDono")
    Page<Pedido> findPedidosByEstabelecimentoAndDono(Long idEstabelecimento, Long idUsuarioDono, Pageable pageable);


}
