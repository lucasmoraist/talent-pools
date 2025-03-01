package com.sobei.banco_de_talentos.domain.model;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Escolaridade {
    private String grau;
    private String formacao;
    private String anoConclusao;
    private String instituicao;
    private String semestre;

    public Escolaridade(CandidateRequest request) {
        this.grau = request.grau();
        this.formacao = request.formacao();
        this.anoConclusao = request.anoConclusao();
        this.instituicao = request.instituicao();
        this.semestre = request.semestre();
    }
}
