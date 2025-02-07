package com.sobei.banco_de_talentos.domain.model;

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
    private String email;
}
