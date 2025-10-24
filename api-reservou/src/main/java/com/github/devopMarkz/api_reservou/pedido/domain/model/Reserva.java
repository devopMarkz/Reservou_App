package com.github.devopMarkz.api_reservou.pedido.domain.model;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "horario_id", nullable = false)
    private Horario horario;

    @Column(name = "data_reserva", nullable = false)
    private LocalDateTime dataReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusReserva status;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacidade")
    private PrivacidadeReserva privacidade;

    @Column(name = "limite_participantes_externos")
    private Integer limiteParticipantesExternos;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "tb_reserva_usuarios",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> participantes = new HashSet<>();

    public Reserva() {
    }

    public Reserva(Pedido pedido, Horario horario, LocalDateTime dataReserva) {
        this.pedido = pedido;
        this.horario = horario;
        this.dataReserva = dataReserva;
    }

    public Set<Usuario> getParticipantes() {
        return Collections.unmodifiableSet(participantes);
    }

    public void adicionarParticipante(Usuario u) {
        this.participantes.add(u);
    }

    public void removerParticipante(Usuario u) {
        this.participantes.remove(u);
    }

}
