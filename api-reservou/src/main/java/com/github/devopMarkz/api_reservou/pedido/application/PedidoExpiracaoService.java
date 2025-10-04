package com.github.devopMarkz.api_reservou.pedido.application;

import com.github.devopMarkz.api_reservou.pedido.domain.model.Pedido;
import com.github.devopMarkz.api_reservou.pedido.domain.model.StatusPedido;
import com.github.devopMarkz.api_reservou.pedido.domain.repository.PedidoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoExpiracaoService {

    private final PedidoRepository pedidoRepository;

    public PedidoExpiracaoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    @Transactional
    public void deletarPedidosExpirados() {
        LocalDateTime agora = LocalDateTime.now();

        List<Pedido> pedidosExpirados = pedidoRepository.findByStatusAndDataExpiracaoBefore(
                StatusPedido.AGUARDANDO_PAGAMENTO, agora
        );

        pedidoRepository.deleteAll(pedidosExpirados);

        System.out.println("Executando metodo para remover pedidos sem pagamento.");
    }

}
