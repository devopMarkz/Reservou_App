package com.github.devopMarkz.api_reservou.domain.repository.estabelecimento.specs;

import com.github.devopMarkz.api_reservou.domain.model.estabelecimento.Estabelecimento;
import org.springframework.data.jpa.domain.Specification;

public class EstabelecimentoSpecifications {

    public static Specification<Estabelecimento> id(Long id) {
        return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);
    }

    public static Specification<Estabelecimento> nome(String nome) {
        return (root, query, cb) -> (nome == null || nome.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> dono(Long idDono) {
        return (root, query, cb) -> idDono == null ? null : cb.equal(root.get("dono").get("id"), idDono);
    }

    public static Specification<Estabelecimento> ativo(Boolean ativo) {
        return (root, query, cb) -> ativo == null ? null : cb.equal(root.get("ativo"), ativo);
    }

    // --- Campos do Endereço ---
    public static Specification<Estabelecimento> logradouro(String logradouro) {
        return (root, query, cb) -> (logradouro == null || logradouro.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("endereco").get("logradouro")), "%" + logradouro.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> numero(String numero) {
        return (root, query, cb) -> (numero == null || numero.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("endereco").get("numero")), "%" + numero.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> complemento(String complemento) {
        return (root, query, cb) -> (complemento == null || complemento.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("endereco").get("complemento")), "%" + complemento.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> bairro(String bairro) {
        return (root, query, cb) -> (bairro == null || bairro.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("endereco").get("bairro")), "%" + bairro.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> cidade(String cidade) {
        return (root, query, cb) -> (cidade == null || cidade.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("endereco").get("cidade")), "%" + cidade.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> estado(String estado) {
        return (root, query, cb) -> (estado == null || estado.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("endereco").get("estado")), "%" + estado.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> cep(String cep) {
        return (root, query, cb) -> (cep == null || cep.isEmpty())
                ? null
                : cb.like(cb.upper(root.get("endereco").get("cep")), "%" + cep.toUpperCase() + "%");
    }

    public static Specification<Estabelecimento> latitude(Double latitude) {
        return (root, query, cb) -> latitude == null ? null : cb.equal(root.get("endereco").get("latitude"), latitude);
    }

    public static Specification<Estabelecimento> longitude(Double longitude) {
        return (root, query, cb) -> longitude == null ? null : cb.equal(root.get("endereco").get("longitude"), longitude);
    }
}
