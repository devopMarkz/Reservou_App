package com.github.devopMarkz.api_reservou.pedido.domain.repository;

import com.github.devopMarkz.api_reservou.pedido.domain.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Buscar reservas de um usuário específico (paginado)
    @Query("""
        SELECT r FROM Reserva r
        JOIN r.pedido p
        WHERE p.usuario.id = :usuarioId
    """)
    Page<Reserva> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);

    // Buscar reservas para um horário específico (paginado)
    Page<Reserva> findByHorarioId(Long horarioId, Pageable pageable);

    // Buscar reservas de um usuário para um horário específico
    @Query("""
        SELECT r FROM Reserva r
        JOIN r.pedido p
        WHERE p.usuario.id = :usuarioId AND r.horario.id = :horarioId
    """)
    Optional<Reserva> findByUsuarioIdAndHorarioId(
            @Param("usuarioId") Long usuarioId,
            @Param("horarioId") Long horarioId
    );

    // Buscar reservas de um usuário para um intervalo de datas
    @Query("""
        SELECT r FROM Reserva r
        JOIN r.pedido p
        WHERE p.usuario.id = :usuarioId
        AND r.dataReserva BETWEEN :dataInicio AND :dataFim
    """)
    Page<Reserva> findByUsuarioAndDataReservaBetween(
            @Param("usuarioId") Long usuarioId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
    );

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Reserva r
        WHERE r.horario.id = :horarioId
          AND CAST(r.dataReserva AS date) = :dia
    """)
    boolean existsByHorarioAndData(@Param("horarioId") Long horarioId, @Param("dia") LocalDate dia);

    @EntityGraph(attributePaths = "participantes")
    @Query("select r from Reserva r where r.id = :id")
    Optional<Reserva> findWithParticipantesById(@Param("id") Long id);

}