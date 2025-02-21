package com.sobei.banco_de_talentos.service.impl;

import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.exceptions.ResourceNotFound;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.repository.CandidatoRepository;
import com.sobei.banco_de_talentos.service.CandidatoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CandidatoServiceImpl implements CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    @Override
    public Candidato save(Candidato request, CargoEnum cargo) {
        log.info("[CandidatoServiceImpl] - Salvando candidato: {}", request);
        Candidato savedCandidato = new Candidato(request, cargo);
        this.repository.save(savedCandidato);
        log.info("[CandidatoServiceImpl] - Candidato salvo com sucesso: {}", savedCandidato);
        return savedCandidato;
    }

    @Override
    public Page<Candidato> findAll(int page, int size, StatusEnum statusEnum, String regiao, CargoEnum cargo) {
        log.info("[CandidatoServiceImpl] - Buscando candidatos com filtros: cargo={}, regiao={}, status={}", cargo, regiao, statusEnum);
        Pageable pageable = PageRequest.of(page, size);
        List<Candidato> candidatos = this.repository.findAll()
                .stream()
                .filter(c -> (cargo == null || c.getCargo().equals(cargo)))
                .filter(c -> (regiao == null || c.getEndereco().getRegiao().equalsIgnoreCase(regiao)))
                .filter(c -> (statusEnum == null || c.getStatus().equals(statusEnum)))
                .collect(Collectors.toList());

        return new PageImpl<>(candidatos, pageable, candidatos.size());
    }

    @Override
    public Candidato findById(String id) {
        log.info("[CandidatoServiceImpl] - Buscando candidato por ID: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("[CandidatoServiceImpl] - Candidato não encontrado para o ID: {}", id);
                    return new ResourceNotFound("Candidato não encontrado");
                });
    }

    @Override
    public void updateStatus(String id, StatusEnum status) {
        log.info("[CandidatoServiceImpl] - Atualizando status do candidato com ID: {}", id);
        Candidato candidato = this.findById(id);

        switch (status) {
            case PENDENTE -> candidato.setStatusPendente();
            case APROVADO -> candidato.setStatusAprovado();
            case EM_ANALISE -> candidato.setStatusEmAnalise();
        }

        this.repository.save(candidato);
        log.info("[CandidatoServiceImpl] - Status do candidato atualizado com sucesso");
    }
}
