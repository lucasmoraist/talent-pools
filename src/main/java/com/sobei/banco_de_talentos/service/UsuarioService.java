package com.sobei.banco_de_talentos.service;

import com.sobei.banco_de_talentos.domain.dto.LoginRequest;
import com.sobei.banco_de_talentos.domain.model.Usuario;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    Usuario findByEmail(String email);
    String login(LoginRequest request);
}
