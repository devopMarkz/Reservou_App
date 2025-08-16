package com.github.devopMarkz.api_reservou.quadra.domain.model;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_quadras")
public class Quadra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "quadra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Horario> horarios = new HashSet<>();

    public Quadra() {
    }

    public Quadra(Long id, String nome, String tipo, Estabelecimento estabelecimento) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.estabelecimento = estabelecimento;
    }

    public Set<Horario> getHorarios() {
        return Collections.unmodifiableSet(this.horarios);
    }
}
