package com.sobei.banco_de_talentos.domain.enums;

import lombok.Getter;

@Getter
public enum CargoEnum {
    PROFESSORA("Professora"),
    AUXILIAR_DE_SALA("Auxiliar de Sala"),
    AUXILIAR_DE_COZINHA("Auxiliar de Cozinha"),
    AUXILIAR_DE_LIMPEZA("Auxiliar de Limpeza"),
    AUXILIZAR_DE_ENFERMAGEM("Auxiliar de Enfermagem"),
    PSICOLOGO("Psicólogo"),
    OUTRO("Outro");

    private final String descricao;

    CargoEnum(String descricao) {
        this.descricao = descricao;
    }
}
