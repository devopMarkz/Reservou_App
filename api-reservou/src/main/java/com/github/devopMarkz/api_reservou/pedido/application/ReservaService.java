package com.github.devopMarkz.api_reservou.pedido.application;

import com.github.devopMarkz.api_reservou.pedido.domain.model.Reserva;
import com.github.devopMarkz.api_reservou.pedido.domain.model.StatusReserva;
import com.github.devopMarkz.api_reservou.pedido.domain.repository.ReservaRepository;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import com.github.devopMarkz.api_reservou.shared.exception.ViolacaoRecursoException;
import com.github.devopMarkz.api_reservou.usuario.application.UsuarioAutenticadoService;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import com.github.devopMarkz.api_reservou.usuario.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void entrarNaReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findWithParticipantesById(reservaId)
                .orElseThrow(() -> new EntidadeInexistenteException("Reserva não encontrada."));

        validarEstadoEditavel(reserva);

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        if (reserva.getParticipantes().contains(usuarioLogado)) {
            throw new ViolacaoRecursoException("Usuário já está participando.");
        }

        if ("PRIVADA".equals(reserva.getPrivacidade().name())) {
            throw new ViolacaoRecursoException("A reserva não está aberta para público externo.");
        }

        if (reserva.getParticipantes().size() >= reserva.getLimiteParticipantesExternos()) {
            throw new ViolacaoRecursoException("Limite de participantes atingido.");
        }

        reserva.adicionarParticipante(usuarioLogado);

        reservaRepository.save(reserva);
    }

    @Transactional
    public void sairDaReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findWithParticipantesById(reservaId)
                .orElseThrow(() -> new EntidadeInexistenteException("Reserva não encontrada."));

        validarEstadoEditavel(reserva);

        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();

        if (!reserva.getParticipantes().contains(usuarioLogado)) {
            throw new ViolacaoRecursoException("Usuário não está participando desta reserva.");
        }

        reserva.removerParticipante(usuarioLogado);

        reservaRepository.save(reserva);
    }

    @Transactional
    public void removerParticipanteAdmin(Long reservaId, Long usuarioId) {
        Reserva reserva = reservaRepository.findWithParticipantesById(reservaId)
                .orElseThrow(() -> new EntidadeInexistenteException("Reserva não encontrada."));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeInexistenteException("Usuário não encontrado."));

        if (!usuarioAutenticadoEAdminOuDono(reserva)) {
            throw new ViolacaoRecursoException("Acesso negado: Apenas admin ou dono da reserva pode remover participantes.");
        }

        if (!reserva.getParticipantes().contains(usuario)) {
            throw new ViolacaoRecursoException("Usuário não está participando desta reserva.");
        }

        reserva.removerParticipante(usuario);
    }

    private boolean usuarioAutenticadoEAdminOuDono(Reserva reserva) {
        Usuario usuarioLogado = UsuarioAutenticadoService.getUsuarioAutenticado();
        boolean ehDono = reserva.getPedido().getUsuario().equals(usuarioLogado);
        boolean ehAdmin = usuarioLogado.getPerfil() != null && "ROLE_ADMINISTRADOR".equals(usuarioLogado.getPerfil().name());
        return ehDono || ehAdmin;
    }

    private void validarEstadoEditavel(Reserva reserva) {
        if (reserva.getStatus() == StatusReserva.CANCELADA || reserva.getStatus() == StatusReserva.CONCLUIDA) {
            throw new IllegalStateException("Esta reserva não pode mais ser alterada.");
        }
    }
}

