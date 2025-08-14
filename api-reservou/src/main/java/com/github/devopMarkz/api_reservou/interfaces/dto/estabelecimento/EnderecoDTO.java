package com.github.devopMarkz.api_reservou.interfaces.dto.estabelecimento;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    @NotBlank(message = "Logradouro precisa ser informado.")
    private String logradouro;

    @NotBlank(message = "Número precisa ser informado.")
    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro precisa ser informado.")
    private String bairro;

    @NotBlank(message = "Cidade precisa ser informada.")
    private String cidade; // localidade no viacep

    @NotBlank(message = "Estado precisa ser informado.")
    private String estado;

    @NotBlank(message = "CEP precisa ser informado.")
    private String cep;

    private Double latitude;
    private Double longitude;
}