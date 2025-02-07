package com.sobei.banco_de_talentos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaProfissional {
    private String empresa;
    private String cargo;
    private String atividades;
    private String admissao;
    private String demissao;
    private String motivoSaida;
}
