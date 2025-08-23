package com.github.devopMarkz.api_reservou.quadra.domain.service;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import com.github.devopMarkz.api_reservou.quadra.domain.model.Quadra;
import com.github.devopMarkz.api_reservou.shared.exception.EntidadeInexistenteException;
import org.springframework.stereotype.Service;

@Service
public class QuadraDomainService {

    public Quadra criarQuadra(Quadra quadra, Estabelecimento estabelecimento) {
        quadra.setEstabelecimento(estabelecimento);
        return quadra;
    }

    // CRIAR UMA CONSULTA PRÓPRIA PARA ISSO
    public void verificarQuadraEmEstabelecimento(Quadra quadra, Estabelecimento estabelecimento){
        if(!estabelecimento.getQuadras().contains(quadra)){
            throw new EntidadeInexistenteException("Esta quadra não pertence a este estabelecimento.");
        }
    }

}
