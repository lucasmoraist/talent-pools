package com.sobei.banco_de_talentos.controller;

import com.sobei.banco_de_talentos.domain.dto.EnderecoDTO;
import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.service.CandidatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Operações relacionadas a candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService service;

    @PostMapping("/save")
    public Candidato save(@RequestParam CargoEnum cargo, @RequestBody @Valid Candidato candidato) {
        log.info("[CandidatoController] - Recebendo solicitação para salvar candidato: {}", candidato);
        Candidato savedCandidato = this.service.save(candidato, cargo);
        log.info("[CandidatoController] - Candidato salvo com sucesso: {}", savedCandidato);
        return savedCandidato;
    }

    @GetMapping
    public Page<Candidato> findAll(
            @RequestParam(value = "cargo", required = true) CargoEnum cargo,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "status", required = false) StatusEnum status,
            @RequestParam(value = "regiao", required = false) String regiao
            ) {
        Page<Candidato> candidatos = this.service.findAll(page, size, status, regiao, cargo);
        log.info("[CandidatoController] - Retornando {} candidatos", candidatos.getTotalElements());
        return candidatos;
    }

    @GetMapping("/{id}")
    public Candidato findById(@PathVariable String id) {
        log.info("[CandidatoController] - Recebendo solicitação para buscar candidato com ID: {}", id);
        Candidato candidato = this.service.findById(id);
        log.info("[CandidatoController] - Candidato encontrado: {}", candidato);
        return candidato;
    }

    @PatchMapping("/{id}")
    public void updateStatus(
            @PathVariable String id,
            @RequestParam("status") StatusEnum status
    ) {
        log.info("[CandidatoController] - Recebendo solicitação para atualizar status do candidato com ID: {}", id);
        this.service.updateStatus(id, status);
        log.info("[CandidatoController] - Status do candidato atualizado");
    }

    @GetMapping("/regions")
    public List<EnderecoDTO> findRegios() {
        log.info("[CandidatoController] - Buscando regiões");
        List<EnderecoDTO> regioes = this.service.findAddress();
        log.info("[CandidatoController] - Regiões encontradas: {}", regioes.size());
        return regioes;
    }

}
