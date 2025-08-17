package com.github.devopMarkz.api_reservou.reserva.domain.model;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.pagamento.domain.model.Pagamento;
import com.github.devopMarkz.api_reservou.pedido.domain.model.Pedido;
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

    public Reserva() {
    }

    public Reserva(Pedido pedido, Horario horario, LocalDateTime dataReserva) {
        this.pedido = pedido;
        this.horario = horario;
        this.dataReserva = dataReserva;
    }

}
