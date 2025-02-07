package com.sobei.banco_de_talentos.domain.exceptions;

public class UniqueException extends RuntimeException {
    public UniqueException(String message) {
        super(message);
    }
}
