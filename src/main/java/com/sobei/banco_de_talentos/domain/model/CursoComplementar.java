package com.sobei.banco_de_talentos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoComplementar {
    private String curso;
    private int anoConclusao;
}
