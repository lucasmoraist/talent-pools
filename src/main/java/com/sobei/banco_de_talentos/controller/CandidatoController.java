package com.sobei.banco_de_talentos.controller;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import com.sobei.banco_de_talentos.domain.dto.EnderecoDTO;
import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.service.CandidatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Operações relacionadas a candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService service;

    @Operation(summary = "Salvar candidato", description = "Salva um candidato no banco de dados")
    @PostMapping("/save")
    public ResponseEntity<Candidato> save(@RequestParam CargoEnum cargo, @RequestBody @Valid Candidato candidato) {
        log.info("[CandidatoController] - Recebendo solicitação para salvar candidato: {}", candidato);
        Candidato savedCandidato = this.service.save(candidato, cargo);
        log.info("[CandidatoController] - Candidato salvo com sucesso: {}", savedCandidato);
        return ResponseEntity.ok(savedCandidato);
    }

    @Operation(summary = "Listar candidatos", description = "Lista todos os candidatos cadastrados no banco de dados", security = {
            @SecurityRequirement(name = "bearer")
    })
    @GetMapping
    public ResponseEntity<Page<Candidato>> findAll(
            @RequestParam(value = "cargo") CargoEnum cargo,
            @RequestParam(value = "status", required = false) StatusEnum status,
            @RequestParam(value = "regiao", required = false) String regiao,
            @RequestParam(value = "nome", required = false) String nome,
            @PageableDefault(value = 10, size = 10, page = 0, direction = Sort.Direction.DESC, sort = {"dataCadastro"}) Pageable pageable
    ) {
        log.info("[CandidatoController] - Recebendo solicitação para buscar todos os candidatos");
        Page<Candidato> candidatos = this.service.findAll(cargo, status, regiao, nome, pageable);
        log.info("[CandidatoController] - Total de candidatos encontrados: {}", candidatos.getTotalElements());
        return ResponseEntity.ok(candidatos);
    }

    @Operation(summary = "Buscar candidato por ID", description = "Busca um candidato no banco de dados pelo ID", security = {
            @SecurityRequirement(name = "bearer")
    })
    @GetMapping("/{id}")
    public Candidato findById(@PathVariable String id) {
        log.info("[CandidatoController] - Recebendo solicitação para buscar candidato com ID: {}", id);
        Candidato candidato = this.service.findById(id);
        log.info("[CandidatoController] - Candidato encontrado: {}", candidato);
        return candidato;
    }

    @Operation(summary = "Atualizar candidato", description = "Atualiza um candidato no banco de dados", security = {
            @SecurityRequirement(name = "bearer")
    })
    @PatchMapping("/{id}")
    public void updateStatus(
            @PathVariable String id,
            @RequestParam("status") StatusEnum status
    ) {
        log.info("[CandidatoController] - Recebendo solicitação para atualizar status do candidato com ID: {}", id);
        this.service.updateStatus(id, status);
        log.info("[CandidatoController] - Status do candidato atualizado");
    }

    @Operation(summary = "Salva candidatos", description = "Método apenas para salvar todos usuários antigos", security = {
            @SecurityRequirement(name = "bearer")
    })
    @PostMapping("save-all")
    public List<Candidato> saveAll(@RequestBody List<CandidateRequest> candidatos) {
        log.info("[CandidatoController] - Recebendo solicitação para salvar candidatos");
        var response = this.service.saveAll(candidatos);
        log.info("[CandidatoController] - Candidatos salvos com sucesso");
        return response;
    }

}
