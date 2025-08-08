package com.github.devopMarkz.api_reservou.domain.model.estabelecimento;

import com.github.devopMarkz.api_reservou.domain.model.avaliacao.Avaliacao;
import com.github.devopMarkz.api_reservou.domain.model.quadra.Quadra;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
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
@Table(schema = "reservou", name = "tb_estabelecimentos")
public class Estabelecimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "usuario_dono_id", nullable = false)
    private Usuario dono;

    @Column(name = "nota_media", nullable = false)
    private Double notaMedia;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Quadra> quadras  = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "estabelecimento", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Estabelecimento() {
        this.notaMedia = this.calculaMedia();
    }

    public Estabelecimento(String nome, String endereco, Usuario dono) {
        this.nome = nome;
        this.endereco = endereco;
        this.dono = dono;
        this.notaMedia = this.calculaMedia();
    }

    private Double calculaMedia() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        Double soma = avaliacoes.stream().map(Avaliacao::getNota).reduce(0.0, Double::sum);
        return soma / avaliacoes.size();
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
    }
}
