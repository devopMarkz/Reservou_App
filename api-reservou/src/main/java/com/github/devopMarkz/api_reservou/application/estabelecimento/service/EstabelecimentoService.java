package com.github.devopMarkz.api_reservou.application.estabelecimento.service;

import com.github.devopMarkz.api_reservou.application.estabelecimento.mapper.EstabelecimentoMapper;
import com.github.devopMarkz.api_reservou.domain.repository.estabelecimento.EstabelecimentoRepository;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final EstabelecimentoMapper estabelecimentoMapper;

    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository,
                                  EstabelecimentoMapper estabelecimentoMapper) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.estabelecimentoMapper = estabelecimentoMapper;
    }
}
