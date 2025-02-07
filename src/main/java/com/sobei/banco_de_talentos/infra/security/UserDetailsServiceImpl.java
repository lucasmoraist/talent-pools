package com.sobei.banco_de_talentos.infra.security;

import com.sobei.banco_de_talentos.domain.model.Usuario;
import com.sobei.banco_de_talentos.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[UserDetailsServiceImpl] - Buscar usuário por e-mail: {}", username);
        Usuario usuario = this.repository.findByEmail(username)
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado");
                    return new UsernameNotFoundException("Usuário não encontrado");
                });
        return new UserDetailsImpl(usuario);
    }

}
