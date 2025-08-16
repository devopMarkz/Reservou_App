package com.github.devopMarkz.api_reservou.estabelecimento.domain.service;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Perfil;
import com.github.devopMarkz.api_reservou.usuario.domain.model.Usuario;
import com.github.devopMarkz.api_reservou.estabelecimento.interfaces.exception.LimiteQuantiaEstabelecimentoException;
import com.github.devopMarkz.api_reservou.shared.exception.ViolacaoRecursoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoDomainService {

    private static final Logger log = LoggerFactory.getLogger(EstabelecimentoDomainService.class);

    public void validarLimiteEstabelecimentos(Usuario usuario) {
        int limite = usuario.getPlano().getQuantidadeEstabelecimentos();

        if(usuario.getEstabelecimentos().size() >= limite){
            throw new LimiteQuantiaEstabelecimentoException("O usuário " + usuario.getEmail() + " atingiu o limite de estabelecimentos por plano.");
        }
    }

    public Estabelecimento criarEstabelecimento(Usuario dono, Estabelecimento estabelecimento) {
        estabelecimento.setDono(dono);
        return estabelecimento;
    }

    // Se não for o dono do estabelecimento ou o administrador do sistema tentando acessar o recurso, lança exceção
    public void validarDonoEstabelecimento(Usuario usuarioLogado, Estabelecimento estabelecimento) {
        if(!usuarioLogado.equals(estabelecimento.getDono())){
            if(Perfil.ROLE_ADMINISTRADOR.equals(usuarioLogado.getPerfil())){
                log.info("Estabelecimento {} alterado pelo administrador {}.", estabelecimento.getId(), usuarioLogado.getId());
            } else {
                throw new ViolacaoRecursoException("Usuário " + usuarioLogado.getEmail() + " não pode atualizar o estabelecimento " + estabelecimento.getNome());
            }
        }
    }

}
