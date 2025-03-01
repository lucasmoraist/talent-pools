package com.sobei.banco_de_talentos.domain.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    DISPONIVEL("Disponível"),
    EM_ANALISE("Em análise"),
    APROVADO("Aprovado");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

}
