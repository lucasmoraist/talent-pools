package com.sobei.banco_de_talentos.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filhos {
    @NotNull(message = "O campo 'temFilhos' é obrigatório")
    private boolean temFilhos;
    private int quantidade;
}
