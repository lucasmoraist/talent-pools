package com.sobei.banco_de_talentos.controller;

import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.service.CandidatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Operações relacionadas a candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService service;

    @Operation(summary = "Salvar candidato", description = "Salva um candidato", security = {
            @SecurityRequirement(name = "bearer")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato salvo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Exception.class)
            ))
    })
    @PostMapping
    public Candidato save(@RequestBody @Valid Candidato candidato) {
        log.info("[CandidatoController] - Recebendo solicitação para salvar candidato: {}", candidato);
        Candidato savedCandidato = this.service.save(candidato);
        log.info("[CandidatoController] - Candidato salvo com sucesso: {}", savedCandidato);
        return savedCandidato;
    }

    @Operation(summary = "Atualizar candidato", description = "Atualiza um candidato", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameters(value = {
            @Parameter(name = "page", description = "Número da página"),
            @Parameter(name = "size", description = "Tamanho da página"),
            @Parameter(name = "isAccepted", description = "Se o candidato já foi aceito")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar candidato")
    })
    @GetMapping
    public Page<Candidato> findAll(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "isAccepted", defaultValue = "false", required = false) boolean isAccepted
    ) {
        log.info("[CandidatoController] - Recebendo solicitação para buscar candidatos - Página: {}, Tamanho: {}, Aceito: {}", page, size, isAccepted);
        Page<Candidato> candidatos = this.service.findAll(page, size, isAccepted);
        log.info("[CandidatoController] - Retornando {} candidatos", candidatos.getTotalElements());
        return candidatos;
    }

    @Operation(summary = "Buscar candidato por ID", description = "Busca um candidato por ID", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameter(name = "id", description = "ID do candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @GetMapping("/{id}")
    public Candidato findById(@PathVariable String id) {
        log.info("[CandidatoController] - Recebendo solicitação para buscar candidato com ID: {}", id);
        Candidato candidato = this.service.findById(id);
        log.info("[CandidatoController] - Candidato encontrado: {}", candidato);
        return candidato;
    }

    @Operation(summary = "Deletar candidato", description = "Deleta um candidato", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameter(name = "id", description = "ID do candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("[CandidatoController] - Recebendo solicitação para deletar candidato com ID: {}", id);
        this.service.delete(id);
        log.info("[CandidatoController] - Candidato com ID {} deletado com sucesso", id);
    }

}
