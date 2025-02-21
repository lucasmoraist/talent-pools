package com.sobei.banco_de_talentos.domain.enums;

import lombok.Getter;

@Getter
public enum SexoEnum {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro"),
    PREFIRO_NAO_INFORMAR("Prefiro não informar");

    private final String descricao;

    SexoEnum(String descricao) {
        this.descricao = descricao;
    }

}
