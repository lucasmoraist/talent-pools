package com.sobei.banco_de_talentos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Escolaridade {
    private String grau;
    private String formacao;
    private int anoConclusao;
    private String instituicao;
}
