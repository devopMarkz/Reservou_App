package com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento;

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
    private EnderecoDTO endereco;
    private Long idDono;
    private Double notaMedia;
    private List<Long> quadras;
    private List<Long> avaliacoes;

}