package com.github.devopMarkz.api_reservou.quadra.domain.repository.specs;

import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import org.springframework.data.jpa.domain.Specification;

public class QuadraSpecifications {

    public static Specification<Quadra> idEstabelecimento(Long idEstabelecimento){
        return (root, query, criteriaBuilder) -> (idEstabelecimento == null)
                ? null :
                criteriaBuilder.equal(root.get("estabelecimento").get("id"), idEstabelecimento);
    }

    public static Specification<Quadra> nome(String nome){
        return (root, query, criteriaBuilder) -> (nome == null || nome.isEmpty())
                ? null
                : criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Quadra> tipo(String tipo){
        return (root, query, criteriaBuilder) -> (tipo == null || tipo.isEmpty())
                ? null
                : criteriaBuilder.like(criteriaBuilder.upper(root.get("tipo")), "%" + tipo.toUpperCase() + "%");
    }

    public static Specification<Quadra> ativo(Boolean ativo){
        return (root, query, criteriaBuilder) -> (ativo == null)
                ? null
                : criteriaBuilder.equal(root.get("ativo"), ativo);
    }

}
