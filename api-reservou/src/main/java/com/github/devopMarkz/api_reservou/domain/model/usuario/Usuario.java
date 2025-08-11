package com.github.devopMarkz.api_reservou.domain.model.usuario;

import com.github.devopMarkz.api_reservou.domain.model.estabelecimento.Estabelecimento;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(schema = "reservou", name = "tb_usuarios")
public class Usuario implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "perfil", nullable = false)
    @Enumerated(EnumType.STRING)
    private Perfil perfil = Perfil.ROLE_USUARIO_COMUM;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @Column(name = "refresh_token_jti")
    private String refreshTokenJti;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "dono", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Estabelecimento> estabelecimentos = new HashSet<>();

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Set<Estabelecimento> getEstabelecimentos() {
        return Collections.unmodifiableSet(estabelecimentos);
    }

    public void desativar(){
        this.ativo = Boolean.FALSE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.perfil.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.ativo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.ativo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.ativo;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}
