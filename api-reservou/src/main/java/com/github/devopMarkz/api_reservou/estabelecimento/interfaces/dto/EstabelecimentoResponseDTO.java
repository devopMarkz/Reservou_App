package com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto;

import com.github.devopMarkz.api_reservou.avaliacao.interfaces.dto.AvaliacaoResponseDTO;
import com.github.devopMarkz.api_reservou.quadra.interfaces.dto.QuadraResponseDTO;
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
    private Double notaMedia;
    private Boolean ativo;
    private List<QuadraResponseDTO> quadras;
    private List<AvaliacaoResponseDTO> avaliacoes;
}