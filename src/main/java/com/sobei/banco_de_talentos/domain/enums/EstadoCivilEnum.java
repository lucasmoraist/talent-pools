package com.sobei.banco_de_talentos.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoCivilEnum {
    SOLTEIRO("Solteiro"),
    CASADO("Casado"),
    DIVORCIADO("Divorciado"),
    VIUVO("Viúvo"),
    OUTRO("Outro");

    private final String descricao;

    EstadoCivilEnum(String descricao) {
        this.descricao = descricao;
    }

}
