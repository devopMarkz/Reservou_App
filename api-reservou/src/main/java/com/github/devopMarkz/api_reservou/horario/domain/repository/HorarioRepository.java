package com.github.devopMarkz.api_reservou.horario.domain.repository;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.horario.interfaces.dto.HorarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>, JpaSpecificationExecutor<Horario> {

    Page<Horario> findByQuadraId(Long quadraId, Pageable pageable);

    Optional<Horario> findByIdAndQuadraId(Long id, Long quadraId);

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

    @Query(value = """
SELECT
    h.id AS id,
    h.quadra_id AS idQuadra,
    r.id AS idReserva,
    h.data_hora_inicio AS dataHoraInicio,
    h.data_hora_fim AS dataHoraFim,
    h.preco AS preco,
    (EXTRACT(EPOCH FROM (h.data_hora_fim - h.data_hora_inicio)) / 60) AS duracaoEmMinutos
FROM
    tb_horarios h
LEFT JOIN
    tb_reservas r ON r.horario_id = h.id AND CAST(r.data_reserva AS DATE) = :dia
WHERE
    h.quadra_id = :quadraId
    AND h.ativo = true
ORDER BY
    h.data_hora_inicio
""", nativeQuery = true)
    Page<Object[]> findHorariosParaDia(
            @Param("quadraId") Long quadraId,
            @Param("dia") LocalDate dia,
            Pageable pageable
    );

}
