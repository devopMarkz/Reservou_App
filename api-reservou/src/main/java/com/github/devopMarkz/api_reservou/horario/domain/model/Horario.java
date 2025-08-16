package com.github.devopMarkz.api_reservou.horario.domain.model;

import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.reserva.domain.model.Reserva;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_horarios")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quadra_id", nullable = false)
    private Quadra quadra;

    // Relacionamento com Reserva (um horário pode ter várias reservas, desde que sejam feitas em dias diferentes)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL)
    private Set<Reserva> reservas = new HashSet<>();

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco = BigDecimal.ZERO;

    @Column(name = "reservado", nullable = false)
    private Boolean reservado = Boolean.FALSE;

    @Transient
    private Duration duracao;

    public Horario() {
        if(this.dataHoraInicio == null || this.dataHoraFim == null) {
            this.duracao = Duration.ZERO;
        } else {
            this.duracao = Duration.between(this.dataHoraInicio, this.dataHoraFim);
        }
    }

    public Horario(Quadra quadra, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, BigDecimal preco) {
        this.quadra = quadra;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.preco = preco;
    }

    public Set<Reserva> getReservas() {
        return Collections.unmodifiableSet(this.reservas);
    }
}
