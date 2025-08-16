package com.github.devopMarkz.api_reservou.estabelecimento.domain.repository.specs;

import com.github.devopMarkz.api_reservou.estabelecimento.domain.model.Estabelecimento;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EstabelecimentoSpecificationBuilder {

    private final List<Specification<Estabelecimento>> specs = new ArrayList<>();

    public EstabelecimentoSpecificationBuilder withId(Long id) {
        if (id != null) specs.add(EstabelecimentoSpecifications.id(id));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withDono(Long idDono) {
        if (idDono != null) specs.add(EstabelecimentoSpecifications.dono(idDono));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withAtivo(Boolean ativo) {
        if(ativo != null) specs.add(EstabelecimentoSpecifications.ativo(ativo));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withNome(String nome) {
        if (nome != null && !nome.isBlank()) specs.add(EstabelecimentoSpecifications.nome(nome));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withLogradouro(String logradouro) {
        if (logradouro != null && !logradouro.isBlank()) specs.add(EstabelecimentoSpecifications.logradouro(logradouro));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withNumero(String numero) {
        if (numero != null && !numero.isBlank()) specs.add(EstabelecimentoSpecifications.numero(numero));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withComplemento(String complemento) {
        if (complemento != null && !complemento.isBlank()) specs.add(EstabelecimentoSpecifications.complemento(complemento));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withBairro(String bairro) {
        if (bairro != null && !bairro.isBlank()) specs.add(EstabelecimentoSpecifications.bairro(bairro));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withCidade(String cidade) {
        if (cidade != null && !cidade.isBlank()) specs.add(EstabelecimentoSpecifications.cidade(cidade));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withEstado(String estado) {
        if (estado != null && !estado.isBlank()) specs.add(EstabelecimentoSpecifications.estado(estado));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withCep(String cep) {
        if (cep != null && !cep.isBlank()) specs.add(EstabelecimentoSpecifications.cep(cep));
        return this;
    }

    public EstabelecimentoSpecificationBuilder withAvaliationsAndQuadrasJoin() {
        specs.add(EstabelecimentoSpecifications.joinFetchAvaliationsAndQuadras());
        return this;
    }

    public Specification<Estabelecimento> build() {
        if (specs.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        Specification<Estabelecimento> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = result.and(specs.get(i));
        }
        return result;
    }
}
