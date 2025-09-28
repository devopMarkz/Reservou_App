package com.github.devopMarkz.api_reservou.horario.domain.repository.specs;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioSpecificationsBuilder {

    private final List<Specification<Horario>> specs = new ArrayList<>();

    public HorarioSpecificationsBuilder withId(Long id){
        if(id != null) specs.add(HorarioSpecifications.id(id));
        return this;
    }

    public HorarioSpecificationsBuilder withQuadraId(Long quadraId){
        if(quadraId != null) specs.add(HorarioSpecifications.quadraId(quadraId));
        return this;
    }

    public HorarioSpecificationsBuilder withDentroDoIntervalo(LocalDateTime dataInicio, LocalDateTime dataFim){
        if(dataInicio != null || dataFim != null) specs.add(HorarioSpecifications.dentroDoIntervalo(dataInicio, dataFim));
        return this;
    }

    public HorarioSpecificationsBuilder withPreco(BigDecimal preco){
        if(preco != null) specs.add(HorarioSpecifications.preco(preco));
        return this;
    }

    public HorarioSpecificationsBuilder withAtivo(Boolean ativo){
        if(ativo != null) specs.add(HorarioSpecifications.ativo(ativo));
        return this;
    }

    public Specification<Horario> build(){
        if(specs.isEmpty()){
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        Specification<Horario> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++){
            result = result.and(specs.get(i));
        }

        return result;
    }

}
