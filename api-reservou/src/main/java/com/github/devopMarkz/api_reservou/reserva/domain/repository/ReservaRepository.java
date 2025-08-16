package com.github.devopMarkz.api_reservou.reserva.domain.repository;

import com.github.devopMarkz.api_reservou.reserva.domain.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Buscar reservas de um usuário específico (paginado)
    Page<Reserva> findByUsuarioId(Long usuarioId, Pageable pageable);

    // Buscar reservas para um horário específico (paginado)
    Page<Reserva> findByHorarioId(Long horarioId, Pageable pageable);

    // Buscar reservas de um usuário para um horário específico
    Optional<Reserva> findByUsuarioIdAndHorarioId(Long usuarioId, Long horarioId);

    // Buscar reservas de um usuário para um intervalo de datas
    @Query("""
        SELECT r FROM Reserva r
        WHERE r.usuario.id = :usuarioId
        AND r.dataReserva BETWEEN :dataInicio AND :dataFim
    """)
    Page<Reserva> findByUsuarioAndDataReservaBetween(
            @Param("usuarioId") Long usuarioId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
    );

    // Buscar todas as reservas com status de pagamento PENDENTE (caso você tenha status de pagamento)
    @Query("""
        SELECT r FROM Reserva r
        JOIN r.pagamentos p
        WHERE p.status = 'PENDENTE'
    """)
    Page<Reserva> findReservationsWithPendingPayments(Pageable pageable);
}
