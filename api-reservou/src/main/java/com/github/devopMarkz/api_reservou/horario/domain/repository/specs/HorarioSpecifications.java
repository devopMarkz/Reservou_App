package com.github.devopMarkz.api_reservou.horario.domain.repository.specs;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HorarioSpecifications {

    public static Specification<Horario> id(Long id){
        return (root, query, criteriaBuilder) -> id == null
                ? null
                : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Horario> quadraId(Long idQuadra){
        return (root, query, criteriaBuilder) -> idQuadra == null
                ? null
                : criteriaBuilder.equal(root.get("quadra").get("id"), idQuadra);
    }

    public static Specification<Horario> dentroDoIntervalo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return (root, query, criteriaBuilder) -> {
            if (dataInicio == null && dataFim == null) {
                return null;
            } else if (dataInicio != null && dataFim != null) {
                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dataHoraInicio"), dataInicio),
                        criteriaBuilder.lessThanOrEqualTo(root.get("dataHoraFim"), dataFim)
                );
            } else if (dataInicio != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dataHoraInicio"), dataInicio);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("dataHoraFim"), dataFim);
            }
        };
    }

    public static Specification<Horario> preco(BigDecimal preco){
        return (root, query, criteriaBuilder) -> preco == null
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), preco);
    }

    public static Specification<Horario> ativo(Boolean ativo){
        return (root, query, criteriaBuilder) -> ativo == null
                ? null
                : criteriaBuilder.equal(root.get("ativo"), ativo);
    }

}
