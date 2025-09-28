package com.github.devopMarkz.api_reservou.horario.domain.repository;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>, JpaSpecificationExecutor<Horario> {

    Page<Horario> findByQuadraId(Long quadraId, Pageable pageable);

    @Query("""
        SELECT h FROM Horario h
        WHERE h.quadra.id = :quadraId
        AND h.dataHoraInicio >= :dataInicio
        AND h.dataHoraFim <= :dataFim
    """)
    Page<Horario> findAvailableHorariosBetweenDates(
            @Param("quadraId") Long quadraId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
    );

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.horario = :horario AND r.dataReserva BETWEEN :dataInicio AND :dataFim")
    boolean isHorarioReservadoNoDia(Horario horario, LocalDateTime dataInicio, LocalDateTime dataFim);

}
