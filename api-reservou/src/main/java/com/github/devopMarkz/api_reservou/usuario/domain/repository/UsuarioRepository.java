package com.github.devopMarkz.api_reservou.usuario.domain.repository;

import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT obj FROM Usuario obj WHERE LOWER(obj.email) = LOWER(:email)")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.estabelecimentos WHERE LOWER(u.email) = LOWER(:email)")
    Optional<Usuario> findByEmailComEstabelecimentos(@Param("email") String email);

    boolean existsByEmail(String email);

    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("""
        SELECT u FROM Usuario u
        WHERE 1 = 1
          AND (:nome IS NULL OR :nome = '' OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
          AND (:email IS NULL OR :email = '' OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
          AND (:perfil IS NULL OR :perfil = '' OR u.perfil = :perfil)
          AND (:ativo IS NULL OR u.ativo = :ativo)
    """)
    Page<Usuario> findByFilters(
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("perfil") String perfil,
            @Param("ativo") Boolean ativo,
            Pageable pageable
    );

}
