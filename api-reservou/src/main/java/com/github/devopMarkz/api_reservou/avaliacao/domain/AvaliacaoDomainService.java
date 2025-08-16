package com.github.devopMarkz.api_reservou.avaliacao.domain;

import com.github.devopMarkz.api_reservou.avaliacao.domain.model.Avaliacao;
import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.shared.exception.ViolacaoRecursoException;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoDomainService {

    public Avaliacao criarAvaliacao(Usuario usuarioLogado, Avaliacao avaliacao, Estabelecimento estabelecimento) {
        avaliacao.setUsuario(usuarioLogado);
        avaliacao.setEstabelecimento(estabelecimento);
        return avaliacao;
    }

    public void validarDonoAvaliacao(Usuario usuarioLogado, Avaliacao avaliacao) {
        if(!usuarioLogado.equals(avaliacao.getUsuario())) {
            throw new ViolacaoRecursoException("Usuário " + usuarioLogado.getEmail() + " não pode atualizar a avaliação " + avaliacao.getId());
        }
    }

    public void validarSeAvaliacaoPertenceAoEstabelecimento(Estabelecimento estabelecimento, Avaliacao avaliacao) {
        if(!avaliacao.getEstabelecimento().equals(estabelecimento)) {
            throw new ViolacaoRecursoException("Avaliação " + avaliacao.getId() + " não pertence ao estabelecimento " + estabelecimento.getId());
        }
    }

}
