package com.sobei.banco_de_talentos.service;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import com.sobei.banco_de_talentos.domain.dto.EnderecoDTO;
import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CandidatoService {
    Candidato save(Candidato candidato, CargoEnum cargo);
    Page<Candidato> findAll(CargoEnum cargo, StatusEnum status, String regiao, Pageable pageable);
    Candidato findById(String id);
    void updateStatus(String id, StatusEnum status);
    List<Candidato> saveAll(List<CandidateRequest> request);
}
