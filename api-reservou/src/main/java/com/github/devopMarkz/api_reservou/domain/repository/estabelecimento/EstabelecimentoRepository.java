package com.github.devopMarkz.api_reservou.domain.repository.estabelecimento;

import com.github.devopMarkz.api_reservou.domain.model.estabelecimento.Estabelecimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {

    @Query("""
        SELECT obj FROM Estabelecimento obj
        WHERE obj.dono.id = :idUsuarioDono
        AND (:id IS NULL OR obj.id = :id)
        AND (:nome IS NULL OR :nome = '' OR UPPER(obj.nome) LIKE UPPER(CONCAT('%', :nome, '%')))
        AND (:endereco IS NULL OR :endereco = '' OR UPPER(obj.endereco) LIKE UPPER(CONCAT('%', :endereco, '%')))
    """)
    Page<Estabelecimento> findByFilters(
            @Param("idUsuarioDono") Long idUsuarioDono,
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("endereco") String endereco,
            Pageable pageable
    );

    @Query("""
        SELECT obj FROM Estabelecimento obj
        JOIN FETCH obj.quadras
        WHERE obj.dono.id = :idUsuarioDono
        AND (:id IS NULL OR obj.id = :id)
    """)
    Optional<Estabelecimento> findByIdWithQuadras(
            @Param("id") Long id,
            @Param("idUsuarioDono") Long idUsuarioDono
    );
}
