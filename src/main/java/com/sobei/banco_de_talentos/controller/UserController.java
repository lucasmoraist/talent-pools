package com.sobei.banco_de_talentos.controller;

import com.sobei.banco_de_talentos.domain.dto.LoginRequest;
import com.sobei.banco_de_talentos.domain.model.Usuario;
import com.sobei.banco_de_talentos.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Salvar usuário", description = "Salva um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar usuário")
    })
    @PostMapping("/save")
    public Usuario save(@RequestBody Usuario usuario) {
        log.info("Recebendo solicitação para salvar usuário: {}", usuario.getEmail());
        Usuario savedUsuario = this.service.save(usuario);
        log.info("Usuário salvo com sucesso: {}", savedUsuario.getEmail());
        return savedUsuario;
    }

    @Operation(summary = "Login", description = "Realiza login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao logar usuário")
    })
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        log.info("Recebendo solicitação de login para o email: {}", request.email());
        String token = this.service.login(request);
        log.info("Usuário logado com sucesso: {}", request.email());
        return token;
    }

}
