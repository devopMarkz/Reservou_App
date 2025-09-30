package com.github.devopMarkz.api_reservou.horario.domain.repository;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.horario.domain.model.HorarioDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HorarioDiaRepository extends JpaRepository<Horario, Long> {

    @Query("SELECT obj FROM HorarioDia obj WHERE obj.horario.id = :horarioId")
    Set<HorarioDia> findAllByHorarioId(@Param("horarioId") Long horarioId);

}
