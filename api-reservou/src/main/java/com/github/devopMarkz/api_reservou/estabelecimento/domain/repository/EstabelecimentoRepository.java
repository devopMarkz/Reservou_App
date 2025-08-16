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

//    @Query("""
//        SELECT obj FROM Estabelecimento obj
//        WHERE obj.dono.id = :idUsuarioDono
//        AND (:id IS NULL OR obj.id = :id)
//        AND (:nome IS NULL OR :nome = '' OR UPPER(obj.nome) LIKE UPPER(CONCAT('%', :nome, '%')))
//        AND (:logradouro IS NULL OR :logradouro = '' OR UPPER(obj.endereco.logradouro) LIKE UPPER(CONCAT('%', :logradouro, '%')))
//        AND (:numero IS NULL OR :numero = '' OR UPPER(obj.endereco.numero) LIKE UPPER(CONCAT('%', :numero, '%')))
//        AND (:complemento IS NULL OR :complemento = '' OR UPPER(obj.endereco.complemento) LIKE UPPER(CONCAT('%', :complemento, '%')))
//        AND (:bairro IS NULL OR :bairro = '' OR UPPER(obj.endereco.bairro) LIKE UPPER(CONCAT('%', :bairro, '%')))
//        AND (:cidade IS NULL OR :cidade = '' OR UPPER(obj.endereco.cidade) LIKE UPPER(CONCAT('%', :cidade, '%')))
//        AND (:estado IS NULL OR :estado = '' OR UPPER(obj.endereco.estado) LIKE UPPER(CONCAT('%', :estado, '%')))
//        AND (:cep IS NULL OR :cep = '' OR UPPER(obj.endereco.cep) LIKE UPPER(CONCAT('%', :cep, '%')))
//    """)
//    Page<Estabelecimento> findByFilters(
//            @Param("idUsuarioDono") Long idUsuarioDono,
//            @Param("id") Long id,
//            @Param("nome") String nome,
//            @Param("logradouro") String logradouro,
//            @Param("numero") String numero,
//            @Param("complemento") String complemento,
//            @Param("bairro") String bairro,
//            @Param("cidade") String cidade,
//            @Param("estado") String estado,
//            @Param("cep") String cep,
//            Pageable pageable
//    );

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

    @Query("SELECT obj FROM Estabelecimento obj " +
            "WHERE obj.dono.id = :idDono " +
            "AND obj.id = :id")
    Optional<Estabelecimento> buscarPorId(
            @Param("id") Long id,
            @Param("idDono") Long idDono
    );
}
