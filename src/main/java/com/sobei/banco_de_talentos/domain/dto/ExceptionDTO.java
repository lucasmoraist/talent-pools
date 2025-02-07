package com.sobei.banco_de_talentos.domain.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(
        String msg,
        HttpStatus status
) {
}
