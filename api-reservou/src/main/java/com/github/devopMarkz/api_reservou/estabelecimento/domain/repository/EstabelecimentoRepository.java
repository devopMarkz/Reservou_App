package com.github.devopMarkz.api_reservou.estabelecimento.domain.repository;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long>, JpaSpecificationExecutor<Estabelecimento> {

    // --- Métodos de Leitura para a API Pública (Sem verificação de dono) ---

    /**
     * Busca um Estabelecimento pelo seu ID, carregando as Quadras associadas.
     * Ideal para o endpoint que lista as quadras de um estabelecimento para o público.
     */
    @Query("SELECT e FROM Estabelecimento e JOIN FETCH e.quadras q WHERE e.id = :id")
    Optional<Estabelecimento> findByIdWithQuadras(@Param("id") Long id);

    /**
     * Busca um Estabelecimento pelo seu ID, carregando as Avaliações.
     * Ideal para o endpoint que exibe as avaliações de um estabelecimento para o público.
     */
    @Query("SELECT e FROM Estabelecimento e JOIN FETCH e.avaliacoes a WHERE e.id = :id")
    Optional<Estabelecimento> findByIdWithAvaliations(@Param("id") Long id);

    /**
     * Busca um Estabelecimento pelo seu ID, carregando Quadras e Avaliações.
     * Usar com cautela devido ao potencial problema de "produto cartesiano" em bases de dados grandes.
     * É mais seguro e performático fazer duas consultas separadas na camada de serviço.
     */
    @Query("SELECT DISTINCT e FROM Estabelecimento e " +
            "LEFT JOIN FETCH e.quadras " +
            "LEFT JOIN FETCH e.avaliacoes " +
            "WHERE e.id = :id")
    Optional<Estabelecimento> findByIdWithAllCollections(@Param("id") Long id);


    // --- Métodos para Operações Restritas (Com verificação de dono) ---

    /**
     * Busca um Estabelecimento pelo seu ID e ID do dono, carregando as Quadras.
     * Usado para garantir que o usuário autenticado é o dono do estabelecimento.
     */
    @Query("SELECT e FROM Estabelecimento e JOIN FETCH e.quadras q WHERE e.id = :id AND e.dono.id = :idDono")
    Optional<Estabelecimento> findByIdAndDonoIdWithQuadras(
            @Param("id") Long id,
            @Param("idDono") Long idDono
    );

    /**
     * Busca um Estabelecimento pelo seu ID e ID do dono, carregando as Avaliações.
     * Usado para garantir que o usuário autenticado é o dono do estabelecimento.
     */
    @Query("SELECT e FROM Estabelecimento e JOIN FETCH e.avaliacoes a WHERE e.id = :id AND e.dono.id = :idDono")
    Optional<Estabelecimento> findByIdAndDonoIdWithAvaliations(
            @Param("id") Long id,
            @Param("idDono") Long idDono
    );

    /**
     * Metodo simples para verificar a existência do Estabelecimento e sua propriedade.
     * Usado em operações como "atualizar" ou "desativar", onde não é necessário carregar as coleções.
     */
    Optional<Estabelecimento> findByIdAndDonoId(Long id, Long idDono);
}