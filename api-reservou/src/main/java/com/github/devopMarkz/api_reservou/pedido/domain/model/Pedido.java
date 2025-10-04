package com.github.devopMarkz.api_reservou.pedido.domain.model;

import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPedido status;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas = new HashSet<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pagamento pagamento;

    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;

    public Pedido(Usuario usuario, StatusPedido status, BigDecimal valorTotal) {
        this.usuario = usuario;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    public void adicionarReserva(Reserva reserva) {
        reserva.setPedido(this);
        this.reservas.add(reserva);
    }

    public void adicionarPagamento(Pagamento pagamento) {
        pagamento.setPedido(this);
        this.pagamento = pagamento;
    }
}
