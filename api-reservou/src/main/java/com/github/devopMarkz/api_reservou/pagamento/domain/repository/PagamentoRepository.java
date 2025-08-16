package com.github.devopMarkz.api_reservou.pagamento.domain.repository;

import com.github.devopMarkz.api_reservou.pagamento.domain.model.Pagamento;
import com.github.devopMarkz.api_reservou.pagamento.domain.model.StatusPagamento;
import com.github.devopMarkz.api_reservou.pagamento.domain.model.TipoPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    // Buscar pagamentos pendentes
    @Query("""
        SELECT p FROM Pagamento p
        WHERE p.status = 'PENDENTE'
    """)
    Page<Pagamento> findPagamentosPendentes(Pageable pageable);

    // Buscar pagamentos de uma reserva específica
    Page<Pagamento> findByReservaId(Long reservaId, Pageable pageable);

    // Buscar pagamentos de um tipo específico (ex: Pix ou Presencial)
    Page<Pagamento> findByTipo(TipoPagamento tipo, Pageable pageable);

    // Buscar pagamentos de um status específico (ex: PAGO ou CANCELADO)
    Page<Pagamento> findByStatus(StatusPagamento status, Pageable pageable);

    // Buscar pagamentos com valor maior que um valor específico
    @Query("""
        SELECT p FROM Pagamento p
        WHERE p.valorPago > :valor
    """)
    Page<Pagamento> findPagamentosByValorPagoGreaterThan(
            @Param("valor") BigDecimal valor,
            Pageable pageable
    );
}
