package com.sobei.banco_de_talentos.domain.dto;

import com.sobei.banco_de_talentos.domain.model.Endereco;

public record EnderecoDTO(
        String regiao
) {
    public EnderecoDTO(Endereco endereco) {
        this(
                endereco.getRegiao()
        );
    }
}
