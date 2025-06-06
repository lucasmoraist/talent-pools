package com.sobei.banco_de_talentos.service.impl;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import com.sobei.banco_de_talentos.domain.dto.EnderecoDTO;
import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.exceptions.ResourceNotFound;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.repository.CandidatoRepository;
import com.sobei.banco_de_talentos.service.CandidatoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidatoServiceImpl implements CandidatoService {

    private final CandidatoRepository repository;
    private final MongoTemplate mongoTemplate;

    @CacheEvict(value = "candidatos", allEntries = true)
    @Override
    public Candidato save(Candidato request, CargoEnum cargo) {
        log.info("[CandidatoServiceImpl] - Salvando candidato: {}", request);
        Candidato savedCandidato = new Candidato(request, cargo);
        repository.save(savedCandidato);
        log.info("[CandidatoServiceImpl] - Candidato salvo com sucesso: {}", savedCandidato);
        return savedCandidato;
    }

    @Cacheable(value = "candidatos", key = "{#cargo, #status, #regiao, #pageable.pageNumber, #pageable.pageSize}")
    @Override
    public Page<Candidato> findAll(CargoEnum cargo, StatusEnum status, String regiao, String nome, Pageable pageable) {
        log.info("[CandidatoServiceImpl] - Buscando todos os candidatos");
        Query query = this.buildFilters(cargo, status, regiao, nome);

        long total = mongoTemplate.count(query, Candidato.class);
        log.info("[CandidatoServiceImpl] - Total de candidatos encontrados: {}", total);

        List<Candidato> candidatos = mongoTemplate.find(query.with(pageable), Candidato.class);

        return new PageImpl<>(candidatos, pageable, total);
    }

    @Override
    public Candidato findById(String id) {
        log.info("[CandidatoServiceImpl] - Buscando candidato por ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("[CandidatoServiceImpl] - Candidato não encontrado para o ID: {}", id);
                    return new ResourceNotFound("Candidato não encontrado");
                });
    }

    @CacheEvict(value = "candidatos", allEntries = true)
    @Override
    public void updateStatus(String id, StatusEnum status) {
        log.info("[CandidatoServiceImpl] - Atualizando status do candidato com ID: {}", id);
        Candidato candidato = findById(id);
        this.updateCandidateStatus(candidato, status);
        repository.save(candidato);
        log.info("[CandidatoServiceImpl] - Status do candidato atualizado com sucesso");
    }

    @Override
    public List<Candidato> saveAll(List<CandidateRequest> requests) {
        log.info("[CandidatoServiceImpl] - Salvando todos os candidatos");
        List<Candidato> candidatos = this.mapRequestsToCandidates(requests);
        repository.saveAll(candidatos);
        log.info("[CandidatoServiceImpl] - Todos os candidatos salvos com sucesso");
        return candidatos;
    }

    private Query buildFilters(CargoEnum cargo, StatusEnum status, String regiao, String nome) {
        Query query = new Query().addCriteria(Criteria.where("cargo").is(cargo));
        log.info("[CandidatoServiceImpl] - Adicionando filtro de cargo: {}", cargo);
        if (status != null) {
            query.addCriteria(Criteria.where("status").is(status));
            log.info("[CandidatoServiceImpl] - Adicionando filtro de status: {}", status);
        }
        if (regiao != null && !regiao.isBlank()) {
            query.addCriteria(Criteria.where("endereco.regiao").regex(regiao, "i"));
            log.info("[CandidatoServiceImpl] - Adicionando filtro de região: {}", regiao);
        }
        if (nome != null && !nome.isBlank()) {
            query.addCriteria(Criteria.where("nome").regex(nome, "i"));
            log.info("[CandidatoServiceImpl] - Adicionando filtro de nome: {}", nome);
        }
        return query;
    }

    private void updateCandidateStatus(Candidato candidato, StatusEnum status) {
        switch (status) {
            case DISPONIVEL -> candidato.setStatusPendente();
            case APROVADO -> candidato.setStatusAprovado();
            case EM_ANALISE -> candidato.setStatusEmAnalise();
        }
    }

    private List<Candidato> mapRequestsToCandidates(List<CandidateRequest> requests) {
        return requests.stream().map(Candidato::new).collect(Collectors.toList());
    }
}
