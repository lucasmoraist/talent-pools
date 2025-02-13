package com.sobei.banco_de_talentos.controller;

import com.sobei.banco_de_talentos.domain.dto.LoginRequest;
import com.sobei.banco_de_talentos.domain.model.Usuario;
import com.sobei.banco_de_talentos.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "Usuário", description = "Operações relacionadas a usuários")
public class UserController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/save")
    public Usuario save(@RequestBody Usuario usuario) {
        log.info("Recebendo solicitação para salvar usuário: {}", usuario.getEmail());
        Usuario savedUsuario = this.service.save(usuario);
        log.info("Usuário salvo com sucesso: {}", savedUsuario.getEmail());
        return savedUsuario;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        log.info("Recebendo solicitação de login para o email: {}", request.email());
        String token = this.service.login(request);
        log.info("Usuário logado com sucesso: {}", request.email());
        return token;
    }

}
