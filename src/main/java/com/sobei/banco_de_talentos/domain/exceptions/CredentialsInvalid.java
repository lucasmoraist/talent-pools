package com.sobei.banco_de_talentos.domain.exceptions;

public class CredentialsInvalid extends RuntimeException {
    public CredentialsInvalid(String message) {
        super(message);
    }
}
