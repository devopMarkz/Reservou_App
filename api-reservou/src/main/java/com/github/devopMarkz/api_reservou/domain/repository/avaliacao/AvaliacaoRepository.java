package com.github.devopMarkz.api_reservou.domain.repository.avaliacao;

import com.github.devopMarkz.api_reservou.domain.model.avaliacao.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    // Buscar avaliações de um estabelecimento específico (paginado)
    Page<Avaliacao> findByEstabelecimentoId(Long estabelecimentoId, Pageable pageable);

    // Buscar avaliações de um usuário específico (paginado)
    Page<Avaliacao> findByUsuarioId(Long usuarioId, Pageable pageable);

    // Buscar avaliações de um estabelecimento com nota maior ou igual a um valor específico (paginado)
    @Query("""
        SELECT a FROM Avaliacao a
        WHERE a.estabelecimento.id = :estabelecimentoId
        AND a.nota >= :nota
    """)
    Page<Avaliacao> findByEstabelecimentoAndNotaGreaterThanEqual(
            @Param("estabelecimentoId") Long estabelecimentoId,
            @Param("nota") Double nota,
            Pageable pageable
    );

    // Buscar todas as avaliações feitas por um usuário com nota superior a um valor
    @Query("""
        SELECT a FROM Avaliacao a
        WHERE a.usuario.id = :usuarioId
        AND a.nota > :nota
    """)
    Page<Avaliacao> findByUsuarioAndNotaGreaterThan(
            @Param("usuarioId") Long usuarioId,
            @Param("nota") Double nota,
            Pageable pageable
    );
}
