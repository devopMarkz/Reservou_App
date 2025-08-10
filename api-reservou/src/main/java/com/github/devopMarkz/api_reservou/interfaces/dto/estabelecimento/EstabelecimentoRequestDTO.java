package com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstabelecimentoRequestDTO {

    @NotBlank(message = "Nome do estabelecimento precisa ser preenchido.")
    private String nome;

    @NotBlank(message = "Endereço precisa ser informado.")
    private String endereco;

    @NotNull(message = "Dono do estabelecimento precisa ser informado.")
    @Positive(message = "ID precisa ser um número maior que 0.")
    private Long idDono;

}
