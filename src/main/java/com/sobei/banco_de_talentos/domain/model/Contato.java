package com.sobei.banco_de_talentos.domain.model;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contato {
    @NotBlank(message = "O campo celular é obrigatório")
    private String celular;
    private String telefone;
    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "O campo email deve ser um email válido")
    private String email;

    public Contato(CandidateRequest request) {
        this.celular = String.valueOf(request.celular());
        this.telefone = String.valueOf(request.telefone());
        this.email = request.email();
    }
}
