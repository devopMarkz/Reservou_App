package com.github.devopMarkz.api_reservou.quadra.domain.repository.specs;

import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QuadraSpecificationBuilder {

    private final List<Specification<Quadra>> quadraSpecifications = new ArrayList<>();

    public QuadraSpecificationBuilder withIdEstabelecimento(Long idEstabelecimento) {
        if(idEstabelecimento != null) quadraSpecifications.add(QuadraSpecifications.idEstabelecimento(idEstabelecimento));
        return this;
    }

    public QuadraSpecificationBuilder withNome(String nome) {
        if(nome != null) quadraSpecifications.add(QuadraSpecifications.nome(nome));
        return this;
    }

    public QuadraSpecificationBuilder withTipo(String tipo) {
        if(tipo != null) quadraSpecifications.add(QuadraSpecifications.tipo(tipo));
        return this;
    }

    public QuadraSpecificationBuilder withAtivo(Boolean ativo) {
        if(ativo != null) quadraSpecifications.add(QuadraSpecifications.ativo(ativo));
        return this;
    }

    public Specification<Quadra> build() {
        if (quadraSpecifications.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        Specification<Quadra> result = quadraSpecifications.get(0);

        for (int i = 1; i < quadraSpecifications.size(); i++) {
            result = result.and(quadraSpecifications.get(i));
        }
        return result;
    }

}
