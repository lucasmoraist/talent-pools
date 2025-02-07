package com.sobei.banco_de_talentos.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sobei.banco_de_talentos.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public String generateToken(String email) {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            log.error("[TokenServiceImpl] - Token é nulo ou vazio");
            throw new RuntimeException("Token é nulo ou vazio");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(email)
                    .sign(algorithm);
            log.info("[TokenServiceImpl] - Token gerado com sucesso");

            return token;
        } catch (Exception e) {
            log.error("[TokenServiceImpl] - Erro ao gerar token", e);
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret);
            String subject = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token)
                    .getSubject();
            log.info("[TokenServiceImpl] - Token validado com sucesso");

            return subject;
        } catch (Exception e) {
            log.error("[TokenServiceImpl] - Erro ao validar token", e);
            throw new RuntimeException("Erro ao validar token");
        }
    }
}
