package com.sobei.banco_de_talentos.service;

import com.sobei.banco_de_talentos.domain.dto.EnderecoDTO;
import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CandidatoService {
    Candidato save(Candidato candidato, CargoEnum cargo);
    Page<Candidato> findAll(int page, int size, StatusEnum status, String regiao, CargoEnum cargo);
    Candidato findById(String id);
    void updateStatus(String id, StatusEnum status);
    List<EnderecoDTO> findAddress(CargoEnum cargo);
}
