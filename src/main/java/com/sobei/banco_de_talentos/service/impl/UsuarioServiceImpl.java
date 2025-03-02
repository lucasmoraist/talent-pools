package com.sobei.banco_de_talentos.service.impl;

import com.sobei.banco_de_talentos.domain.dto.LoginRequest;
import com.sobei.banco_de_talentos.domain.exceptions.CredentialsInvalid;
import com.sobei.banco_de_talentos.domain.exceptions.ResourceNotFound;
import com.sobei.banco_de_talentos.domain.exceptions.UniqueException;
import com.sobei.banco_de_talentos.domain.model.Usuario;
import com.sobei.banco_de_talentos.repository.UsuarioRepository;
import com.sobei.banco_de_talentos.service.TokenService;
import com.sobei.banco_de_talentos.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public Usuario save(Usuario request) {
        validarUsuarioExistente(request.getEmail());
        Usuario usuario = criarUsuario(request);
        Usuario savedUsuario = this.repository.save(usuario);
        log.info("[UsuarioServiceImpl] - Usuário salvo com sucesso: {}", savedUsuario.getEmail());
        return savedUsuario;
    }

    private void validarUsuarioExistente(String email) {
        if (this.repository.findByEmail(email).isPresent()) {
            log.error("[UsuarioServiceImpl] - Usuário já cadastrado: {}", email);
            throw new UniqueException("E-mail já cadastrado");
        }
    }

    private Usuario criarUsuario(Usuario request) {
        return Usuario.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .build();
    }

    @Override
    public Usuario findByEmail(String email) {
        log.info("[UsuarioServiceImpl] - Buscando usuário pelo email: {}", email);
        return this.repository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("[UsuarioServiceImpl] - Usuário não encontrado: {}", email);
                    return new ResourceNotFound("Usuário não encontrado");
                });
    }

    @Override
    public String login(LoginRequest request) {
        log.info("[UsuarioServiceImpl] - Tentativa de login para o email: {}", request.email());
        Usuario usuario = validarCredenciais(request);
        return gerarToken(usuario);
    }

    private Usuario validarCredenciais(LoginRequest request) {
        Usuario usuario = findByEmail(request.email());
        log.info("[UsuarioServiceImpl] - Usuário encontrado: {}", usuario.getEmail());

        if (!this.passwordEncoder.matches(request.password(), usuario.getPassword())) {
            log.error("[UsuarioServiceImpl] - Senha inválida para o email: {}", request.email());
            throw new CredentialsInvalid("E-mail ou senha inválida");
        }
        return usuario;
    }

    private String gerarToken(Usuario usuario) {
        String token = this.tokenService.generateToken(usuario.getEmail());
        log.info("[UsuarioServiceImpl] - Usuário logado com sucesso: {}", usuario.getEmail());
        return token;
    }
}
