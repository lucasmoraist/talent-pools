package com.sobei.banco_de_talentos.domain.dto;

public record LoginRequest(
        String email,
        String password
) {
}
