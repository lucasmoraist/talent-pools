package com.sobei.banco_de_talentos.infra.security;

import com.sobei.banco_de_talentos.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);

        if (token != null) {
            log.info("[SecurityFilter] Token encontrado");
            String login = this.tokenService.validateToken(token);

            if (login != null) {
                UserDetails user = this.userService.loadUserByUsername(login);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("[SecurityFilter] Usuário autenticado com sucesso");
                } else {
                    log.error("[SecurityFilter] Usuário não encontrado");
                }
            } else {
                log.error("[SecurityFilter] Token inválido");
            }
        } else {
            log.error("[SecurityFilter] Token não encontrado");
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");

        if (authHeader == null) {
            log.info("[SecurityFilter] Token não informado");
            return null;
        }

        String token = authHeader.replace("Bearer ", "");
        log.info("[SecurityFilter] Token recuperado com sucesso");
        return token;
    }
}
