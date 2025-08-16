package com.github.devopMarkz.api_reservou.estabelecimento.domain.model;

import com.github.devopMarkz.api_reservou.avaliacao.domain.model.Avaliacao;
import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_estabelecimentos")
public class Estabelecimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "telefone")
    private String telefone;

    @Embedded
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "usuario_dono_id", nullable = false)
    private Usuario dono;

    @Transient
    private Double notaMedia;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Quadra> quadras = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "estabelecimento", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Estabelecimento() {
    }

    public Estabelecimento(String nome, Endereco endereco, Usuario dono) {
        this.nome = nome;
        this.endereco = endereco;
        this.dono = dono;
    }

    public Double getNotaMedia() {
        if (this.avaliacoes == null || this.avaliacoes.isEmpty()) {
            return 0.0;
        }
        return calculaMedia();
    }

    private double calculaMedia() {
        return this.avaliacoes.stream()
                .mapToDouble(Avaliacao::getNota)
                .average()
                .orElse(0.0);
    }

    public Set<Quadra> getQuadras() {
        return Collections.unmodifiableSet(this.quadras);
    }

    public Set<Avaliacao> getAvaliacoes() {
        return Collections.unmodifiableSet(this.avaliacoes);
    }

    public void adicionaQuadra(Quadra quadra) {
        this.quadras.add(quadra);
    }

    public void adicionaAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
        this.calculaMedia();
    }

    public void desativar(){
        this.ativo = Boolean.FALSE;
    }
}
