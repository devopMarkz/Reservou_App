package com.github.devopMarkz.api_reservou.quadra.domain.repository.specs;

import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import org.springframework.data.jpa.domain.Specification;

public class QuadraSpecification {

    public static Specification<Quadra> id(Long id){
        return (root, query, criteriaBuilder) -> (id == null?) null : null;
    }

}
