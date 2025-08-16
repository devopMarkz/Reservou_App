package com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String telefone;
    private EnderecoDTO endereco;
    private Long idDono;
    private Double notaMedia;
    private Boolean ativo;
    private List<Long> quadras;
    private List<Long> avaliacoes;

}