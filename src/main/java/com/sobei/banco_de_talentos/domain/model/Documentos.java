package com.sobei.banco_de_talentos.domain.model;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Documentos {
    @NotBlank(message = "O campo RG é obrigatório")
    private String rg;
    @NotBlank(message = "O campo orgão emissor é obrigatório")
    private String orgaoEmissor;
    @NotBlank(message = "O campo cpf é obrigatório")
    private String cpf;
    @NotBlank(message = "Informe sua carteira de trabalho")
    private String carteiraTrabalho;
    @NotBlank(message = "Informe a série da carteira de trabalho")
    private String serie;
    @NotBlank(message = "Informe o título de eleitor")
    private String tituloEleitor;
    @NotBlank(message = "Informe a zona da seção eleitoral")
    private String zonaSecaoUF;
    @NotBlank(message = "Informe o pis")
    private String pis;
    private boolean documentosIdentificados;

    public Documentos(CandidateRequest request) {
        this.rg = String.valueOf(request.rg());
        this.orgaoEmissor = request.orgaoEmissor();
        this.cpf = String.valueOf(request.cpf());
        this.carteiraTrabalho = String.valueOf(request.carteiraTrabalho());
        this.serie = request.serie();
        this.tituloEleitor = String.valueOf(request.tituloEleitor());
        this.zonaSecaoUF = request.zonaSecaoUF();
        this.pis = String.valueOf(request.pis());
        this.documentosIdentificados = request.documentosIdentificados().equals("Sim");
    }
}
