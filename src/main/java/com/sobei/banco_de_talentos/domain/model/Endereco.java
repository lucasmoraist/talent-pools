package com.sobei.banco_de_talentos.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @NotBlank(message = "O campo 'cep' é obrigatório")
    private String cep;
    @NotBlank(message = "O campo 'regiao' é obrigatório")
    private String regiao;
    @NotBlank(message = "O campo 'rua' é obrigatório")
    private String rua;
    @NotBlank(message = "O campo 'bairro' é obrigatório")
    private String bairro;
}
