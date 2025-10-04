package com.github.devopMarkz.api_reservou.reserva.domain.model;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    public Reserva() {
    }

    public Reserva(Pedido pedido, Horario horario, LocalDateTime dataReserva) {
        this.pedido = pedido;
        this.horario = horario;
        this.dataReserva = dataReserva;
    }

}
