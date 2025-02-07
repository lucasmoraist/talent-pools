package com.sobei.banco_de_talentos.service;

public interface TokenService {
    String generateToken(String email);
    String validateToken(String token);
}
