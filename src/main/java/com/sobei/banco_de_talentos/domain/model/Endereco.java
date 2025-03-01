package com.sobei.banco_de_talentos.domain.model;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
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
    private int numero;
    private String complemento;

    public Endereco(CandidateRequest request) {
        this.cep = request.cep();
        this.regiao = request.regiao();
        this.rua = request.rua();
        this.bairro = request.bairro();
    }

}
