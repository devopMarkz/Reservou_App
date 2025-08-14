package com.github.devopMarkz.api_reservou.domain.service.estabelecimento;

import com.github.devopMarkz.api_reservou.domain.model.estabelecimento.Estabelecimento;
import com.github.devopMarkz.api_reservou.domain.model.usuario.Usuario;
import com.github.devopMarkz.api_reservou.infraestructure.exception.LimiteQuantiaEstabelecimentoException;
import com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento.EstabelecimentoRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoDomainService {

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

}
