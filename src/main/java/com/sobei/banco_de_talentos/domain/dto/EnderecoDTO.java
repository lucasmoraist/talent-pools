package com.sobei.banco_de_talentos.domain.dto;

import com.sobei.banco_de_talentos.domain.model.Candidato;

public record EnderecoDTO(
        String bairro
) {
    public EnderecoDTO(Candidato candidato) {
        this(
                candidato.getEndereco().getBairro()
        );
    }
}
