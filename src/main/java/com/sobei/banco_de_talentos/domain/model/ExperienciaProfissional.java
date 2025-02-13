package com.sobei.banco_de_talentos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaProfissional {
    private String empresa;
    private String cargo;
    private String atividades;
    private LocalDate admissao;
    private LocalDate demissao;
    private String motivoSaida;
}
