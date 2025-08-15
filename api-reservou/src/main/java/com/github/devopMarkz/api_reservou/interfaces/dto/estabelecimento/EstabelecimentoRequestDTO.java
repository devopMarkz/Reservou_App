package com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstabelecimentoRequestDTO {

    @NotBlank(message = "Nome do estabelecimento precisa ser preenchido.")
    private String nome;

    private String descricao;

    private String telefone;

    @Valid
    @NotNull(message = "Endereço precisa ser informado.")
    private EnderecoDTO endereco;

}
