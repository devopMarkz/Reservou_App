package com.github.devopMarkz.api_reservou.domain.model.estabelecimento;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Endereco {

    @Column(name = "logradouro", nullable = false)
    private String logradouro; // Rua, avenida, travessa etc.

    @Column(name = "numero", nullable = false)
    private String numero; // Pode ser "123", "s/n", "Bloco A" etc.

    @Column(name = "complemento")
    private String complemento; // Apartamento, bloco, sala

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

}
