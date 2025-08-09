package com.github.devopMarkz.api_reservou.domain.repository.horario;

import com.github.devopMarkz.api_reservou.domain.model.horario.Horario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    Page<Horario> findByQuadraId(Long quadraId, Pageable pageable);

    Page<Horario> findByQuadraIdAndReservadoFalse(Long quadraId, Pageable pageable);

    @Query("""
        SELECT h FROM Horario h
        WHERE h.quadra.id = :quadraId
        AND h.dataHoraInicio >= :dataInicio
        AND h.dataHoraFim <= :dataFim
        AND h.reservado = false
    """)
    Page<Horario> findAvailableHorariosBetweenDates(
            @Param("quadraId") Long quadraId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim,
            Pageable pageable
    );

}
