package com.github.devopMarkz.api_reservou.estabelecimento.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstabelecimentoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String telefone;
    private String urlFoto;
    private String urlFacebook;
    private String urlInstagram;
    private String urlSite;
    private EnderecoDTO endereco;
    private Double notaMedia;
    private Boolean ativo;
    private List<QuadraResponseDTO> quadras;
    private List<AvaliacaoResponseDTO> avaliacoes;
}