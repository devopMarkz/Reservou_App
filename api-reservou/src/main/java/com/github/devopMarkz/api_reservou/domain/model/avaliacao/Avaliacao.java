package com.github.devopMarkz.api_reservou.domain.model.avaliacao;

import com.github.devopMarkz.api_reservou.domain.model.estabelecimento.Estabelecimento;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    @Column(name = "nota", nullable = false)
    private Double nota;

    @Column(name = "comentario", nullable = true, columnDefinition = "TEXT")
    private String comentario;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Avaliacao() {
    }

    public Avaliacao(Long id, Usuario usuario, Estabelecimento estabelecimento, Double nota) {
        this.id = id;
        this.usuario = usuario;
        this.estabelecimento = estabelecimento;
        this.nota = nota;
    }

    public Avaliacao(Long id, Usuario usuario, Estabelecimento estabelecimento, Double nota, String comentario) {
        this.id = id;
        this.usuario = usuario;
        this.estabelecimento = estabelecimento;
        this.nota = nota;
        this.comentario = comentario;
    }
}
