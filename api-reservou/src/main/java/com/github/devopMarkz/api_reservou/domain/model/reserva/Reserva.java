package com.github.devopMarkz.api_reservou.domain.model.reserva;

import com.github.devopMarkz.api_reservou.domain.model.horario.Horario;
import com.github.devopMarkz.api_reservou.domain.model.pagamento.Pagamento;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(schema = "reservou", name = "tb_reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "horario_id", nullable = false)
    private Horario horario;

    // Relacionamento com Pagamento (uma reserva pode ter vários pagamentos (caso alguém cancele e outro queira alugar))
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Set<Pagamento> pagamentos = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_reserva", nullable = false)
    private LocalDateTime dataReserva;

    public Reserva() {
    }

    public Reserva(Usuario usuario, Horario horario, LocalDateTime dataReserva) {
        this.usuario = usuario;
        this.horario = horario;
        this.dataReserva = dataReserva;
    }

    public Set<Pagamento> getPagamentos() {
        return Collections.unmodifiableSet(this.pagamentos);
    }
}
