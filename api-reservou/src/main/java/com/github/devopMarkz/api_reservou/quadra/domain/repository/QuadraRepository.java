package com.github.devopMarkz.api_reservou.quadra.domain.repository;

import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadraRepository extends JpaRepository<Quadra, Long>, JpaSpecificationExecutor<Quadra> {

    @Query("""
        SELECT obj FROM Quadra obj
        JOIN FETCH obj.horarios
        WHERE 1 = 1
        AND (:id IS NULL OR obj.id = :id)
        AND (:nome IS NULL OR :nome = '' OR UPPER(obj.nome) LIKE UPPER(CONCAT('%', :nome, '%')))
        AND (:tipo IS NULL OR :tipo = '' OR UPPER(obj.tipo) LIKE UPPER(CONCAT('%', :tipo, '%')))
        AND (:idEstabelecimento IS NULL OR obj.estabelecimento.id = :idEstabelecimento)
    """)
    Page<Quadra> findByFilters(
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("tipo") String tipo,
            @Param("idEstabelecimento") Long idEstabelecimento,
            Pageable pageable
    );
}
