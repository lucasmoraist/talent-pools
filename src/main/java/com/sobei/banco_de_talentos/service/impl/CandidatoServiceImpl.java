package com.sobei.banco_de_talentos.service.impl;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import com.sobei.banco_de_talentos.domain.dto.EnderecoDTO;
import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.exceptions.ResourceNotFound;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.repository.CandidatoRepository;
import com.sobei.banco_de_talentos.service.CandidatoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CandidatoServiceImpl implements CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    @CacheEvict(value = "candidatos", allEntries = true)
    @Override
    public Candidato save(Candidato request, CargoEnum cargo) {
        log.info("[CandidatoServiceImpl] - Salvando candidato: {}", request);
        Candidato savedCandidato = new Candidato(request, cargo);
        this.repository.save(savedCandidato);
        log.info("[CandidatoServiceImpl] - Candidato salvo com sucesso: {}", savedCandidato);
        return savedCandidato;
    }

    @Cacheable(value = "candidatos")
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

    @CacheEvict(value = "candidatos", allEntries = true)
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

    @Cacheable(value = "candidatos")
    @Override
    public List<EnderecoDTO> findAddress(CargoEnum cargo) {
        log.info("[CandidatoServiceImpl] - Buscando endereços dos candidatos");
        return this.repository.findAll()
                .stream()
                .filter(c -> c.getCargo().equals(cargo))
                .map(EnderecoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Candidato> saveAll(List<CandidateRequest> request) {
        log.info("[CandidatoServiceImpl] - Salvando todos os candidatos");
        List<Candidato> candidatos = request.stream()
                .map(Candidato::new)
                .collect(Collectors.toList());

        this.repository.saveAll(candidatos);
        log.info("[CandidatoServiceImpl] - Todos os candidatos salvos com sucesso");

        return candidatos;
    }
}
