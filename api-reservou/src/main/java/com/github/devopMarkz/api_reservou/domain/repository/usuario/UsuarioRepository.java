package com.github.devopMarkz.api_reservou.domain.repository.usuario;

import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT obj FROM Usuario obj WHERE obj.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
