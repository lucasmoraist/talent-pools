package com.sobei.banco_de_talentos.service;

import com.sobei.banco_de_talentos.domain.model.Candidato;
import org.springframework.data.domain.Page;

public interface CandidatoService {
    Candidato save(Candidato candidato);
    Page<Candidato> findAll(int page, int size, boolean isAccepted);
    Candidato findById(String id);
    void approved(String id);
}
