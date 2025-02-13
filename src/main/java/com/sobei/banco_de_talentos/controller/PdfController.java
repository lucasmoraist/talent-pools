package com.sobei.banco_de_talentos.controller;

import com.sobei.banco_de_talentos.service.PdfService;
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

    @GetMapping("{id}")
    public ResponseEntity<byte[]> generate(@PathVariable String id) {
        byte[] pdf = this.pdfService.generate(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=curriculo.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
