package com.sobei.banco_de_talentos.service.impl;

import com.sobei.banco_de_talentos.domain.exceptions.ResourceNotFound;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.repository.CandidatoRepository;
import com.sobei.banco_de_talentos.service.CandidatoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CandidatoServiceImpl implements CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    @Override
    public Candidato save(Candidato request) {
        log.info("[CandidatoServiceImpl] - Salvando candidato: {}", request);
        Candidato savedCandidato = this.repository.save(request);
        log.info("[CandidatoServiceImpl] - Candidato salvo com sucesso: {}", savedCandidato);
        return savedCandidato;
    }

    @Override
    public Page<Candidato> findAll(int page, int size, boolean isAccepted) {
        log.info("[CandidatoServiceImpl] - Buscando todos os candidatos - Página: {}, Tamanho: {}, Aceito: {}", page, size, isAccepted);
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        List<Candidato> candidatos = this.repository.findAll()
                .stream()
                .filter(c -> c.getIsAccepted().equals(isAccepted))
                .toList();

        log.info("[CandidatoServiceImpl] - Foram encontrados {} candidatos", candidatos.size());
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
    public void delete(String id) {
        log.info("[CandidatoServiceImpl] - Deletando candidato com ID: {}", id);
        Candidato candidato = this.findById(id);
        this.repository.delete(candidato);
        log.info("[CandidatoServiceImpl] - Candidato com ID: {} deletado com sucesso", id);
    }
}
