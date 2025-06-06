package com.sobei.banco_de_talentos.controller;

import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.service.CandidatoService;
import com.sobei.banco_de_talentos.service.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;
    @Autowired
    private CandidatoService candidatoService;

    @Operation(summary = "Gerar currículo em PDF", description = "Gera um PDF com base no ID do candidato", security = {
            @SecurityRequirement(name = "bearer")
    })
    @GetMapping("{id}")
    public ResponseEntity<byte[]> generate(@PathVariable String id) {
        byte[] pdf = this.pdfService.generate(id);
        Candidato candidato = this.candidatoService.findById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Curriculo "+candidato.getNome()+".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
