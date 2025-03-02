package com.sobei.banco_de_talentos.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.domain.model.Escolaridade;
import com.sobei.banco_de_talentos.domain.model.ExperienciaProfissional;
import com.sobei.banco_de_talentos.service.CandidatoService;
import com.sobei.banco_de_talentos.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final CandidatoService candidatoService;

    @Override
    public byte[] generate(String id) {
        Candidato candidato = this.candidatoService.findById(id);
        String imagePath = "src/main/resources/assets/logo-sobei.png";

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            ImageData imageData = ImageDataFactory.create(imagePath);
            Image image = new Image(imageData);
            image.setWidth(200);
            image.setHeight(60);
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);

            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            document.add(image);

            if (candidato.getNome() != null && !candidato.getNome().isEmpty()) {
                document.add(new Paragraph(new Text(candidato.getNome()).setFont(boldFont).setFontSize(18)));
            }

            if (candidato.getDataNascimento() != null) {
                document.add(new Paragraph(new Text("Data de nascimento: ").setFont(boldFont))
                        .add(new Text(parseDate(candidato.getDataNascimento())).setFont(regularFont)));
            }

            // Adicionar idade
            if (candidato.getIdade() > 0) {
                document.add(new Paragraph(new Text("Idade: ").setFont(boldFont))
                        .add(new Text(String.valueOf(candidato.getIdade())).setFont(regularFont)));
            }

            // Adicionar sexo
            if (candidato.getSexo() != null && candidato.getSexo().getDescricao() != null) {
                document.add(new Paragraph(new Text("Sexo: ").setFont(boldFont))
                        .add(new Text(candidato.getSexo().getDescricao()).setFont(regularFont)));
            }

            // Adicionar estado civil
            if (candidato.getEstadoCivil() != null && candidato.getEstadoCivil().getDescricao() != null) {
                document.add(new Paragraph(new Text("Estado civil: ").setFont(boldFont))
                        .add(new Text(candidato.getEstadoCivil().getDescricao()).setFont(regularFont)));
            }

            // Adicionar cargo pretendido
            if (candidato.getCargo() != null && candidato.getCargo().getDescricao() != null) {
                document.add(new Paragraph(new Text("\nCargo pretendido:").setFont(boldFont).setFontSize(18)));
                document.add(new Paragraph(new Text(candidato.getCargo().getDescricao()).setFont(regularFont)));
            }

            // Adicionar contato
            if (candidato.getContato() != null) {
                document.add(new Paragraph(new Text("\nContato:").setFont(boldFont).setFontSize(18)));

                if (candidato.getContato().getCelular() != null && !candidato.getContato().getCelular().isEmpty()) {
                    document.add(new Paragraph(new Text("Celular: ").setFont(boldFont))
                            .add(new Text(candidato.getContato().getCelular()).setFont(regularFont)));
                }
                if (candidato.getContato().getTelefone() != null && !candidato.getContato().getTelefone().isEmpty()) {
                    document.add(new Paragraph(new Text("Telefone: ").setFont(boldFont))
                            .add(new Text(candidato.getContato().getTelefone()).setFont(regularFont)));
                }
                if (candidato.getContato().getEmail() != null && !candidato.getContato().getEmail().isEmpty()) {
                    document.add(new Paragraph(new Text("E-mail: ").setFont(boldFont))
                            .add(new Text(candidato.getContato().getEmail()).setFont(regularFont)));
                }
            }

            // Adicionar endereço
            if (candidato.getEndereco() != null) {
                document.add(new Paragraph(new Text("\nEndereço:").setFont(boldFont).setFontSize(18)));

                if (candidato.getEndereco().getCep() != null && !candidato.getEndereco().getCep().isEmpty()) {
                    document.add(new Paragraph(new Text("CEP: ").setFont(boldFont))
                            .add(new Text(candidato.getEndereco().getCep()).setFont(regularFont)));
                }
                if (candidato.getEndereco().getRegiao() != null && !candidato.getEndereco().getRegiao().isEmpty()) {
                    document.add(new Paragraph(new Text("Região: ").setFont(boldFont))
                            .add(new Text(candidato.getEndereco().getRegiao()).setFont(regularFont)));
                }
                if (candidato.getEndereco().getRua() != null && !candidato.getEndereco().getRua().isEmpty()) {
                    document.add(new Paragraph(new Text("Rua: ").setFont(boldFont))
                            .add(new Text(candidato.getEndereco().getRua()).setFont(regularFont)));
                }
                if (candidato.getEndereco().getBairro() != null && !candidato.getEndereco().getBairro().isEmpty()) {
                    document.add(new Paragraph(new Text("Bairro: ").setFont(boldFont))
                            .add(new Text(candidato.getEndereco().getBairro()).setFont(regularFont)));
                }
            }

            // Adicionar documentos
            if (candidato.getDocumentos() != null) {
                document.add(new Paragraph(new Text("\nDocumentos:").setFont(boldFont).setFontSize(18)));

                if (candidato.getDocumentos().getRg() != null && !candidato.getDocumentos().getRg().isEmpty()) {
                    document.add(new Paragraph(new Text("RG: ").setFont(boldFont))
                            .add(new Text(candidato.getDocumentos().getRg()).setFont(regularFont)));
                }
                if (candidato.getDocumentos().getCpf() != null && !candidato.getDocumentos().getCpf().isEmpty()) {
                    document.add(new Paragraph(new Text("CPF: ").setFont(boldFont))
                            .add(new Text(candidato.getDocumentos().getCpf()).setFont(regularFont)));
                }
                if (candidato.getDocumentos().getCarteiraTrabalho() != null && !candidato.getDocumentos().getCarteiraTrabalho().isEmpty()) {
                    document.add(new Paragraph(new Text("Carteira de trabalho: ").setFont(boldFont))
                            .add(new Text(candidato.getDocumentos().getCarteiraTrabalho()).setFont(regularFont)));
                }
                // Continue para outros documentos...
            }

            // Adicionar filhos
            if (candidato.getFilhos().isTemFilhos() && candidato.getFilhos().getQuantidade() > 0) {
                document.add(new Paragraph(new Text("\nFilhos:").setFont(boldFont).setFontSize(18)));
                document.add(new Paragraph(new Text("Quantidade de filhos: ").setFont(boldFont))
                        .add(new Text(String.valueOf(candidato.getFilhos().getQuantidade())).setFont(regularFont)));
            }

            // Adicionar escolaridade
            if (!candidato.getEscolaridades().isEmpty()) {
                document.add(new Paragraph(new Text("\nEscolaridade:").setFont(boldFont).setFontSize(18)));
                for (Escolaridade escolaridade : candidato.getEscolaridades()) {
                    if (escolaridade.getGrau() != null && !escolaridade.getGrau().isEmpty()) {
                        document.add(new Paragraph(new Text("\nGrau: ").setFont(boldFont))
                                .add(new Text(escolaridade.getGrau()).setFont(regularFont)));
                    }
                    if (escolaridade.getFormacao() != null && !escolaridade.getFormacao().isEmpty()) {
                        document.add(new Paragraph(new Text("Formação: ").setFont(boldFont))
                                .add(new Text(escolaridade.getFormacao()).setFont(regularFont)));
                    }
                    if (escolaridade.getAnoConclusao() != null) {
                        document.add(new Paragraph(new Text("Ano de conclusão: ").setFont(boldFont))
                                .add(new Text(escolaridade.getAnoConclusao()).setFont(regularFont)));
                    }
                    if (escolaridade.getInstituicao() != null && !escolaridade.getInstituicao().isEmpty()) {
                        document.add(new Paragraph(new Text("Instituição: ").setFont(boldFont))
                                .add(new Text(escolaridade.getInstituicao()).setFont(regularFont)));
                    }
                }
            }

            // Adicionar experiência profissional
            if (!candidato.getExperienciaProfissional().isEmpty()) {
                document.add(new Paragraph(new Text("\nExperiência profissional:").setFont(boldFont).setFontSize(18)));
                for (ExperienciaProfissional experiencia : candidato.getExperienciaProfissional()) {
                    if (experiencia.getEmpresa() != null && !experiencia.getEmpresa().isEmpty()) {
                        document.add(new Paragraph(new Text("\nEmpresa: ").setFont(boldFont))
                                .add(new Text(experiencia.getEmpresa()).setFont(regularFont)));
                    }
                    if (experiencia.getCargo() != null && !experiencia.getCargo().isEmpty()) {
                        document.add(new Paragraph(new Text("Cargo: ").setFont(boldFont))
                                .add(new Text(experiencia.getCargo()).setFont(regularFont)));
                    }
                    if (experiencia.getAtividades() != null && !experiencia.getAtividades().isEmpty()) {
                        document.add(new Paragraph(new Text("Descrição: ").setFont(boldFont))
                                .add(new Text(experiencia.getAtividades()).setFont(regularFont)));
                    }
                    if (experiencia.getAdmissao() != null) {
                        document.add(new Paragraph(new Text("Admissão: ").setFont(boldFont))
                                .add(new Text(experiencia.getAdmissao().toString()).setFont(regularFont)));
                    }
                    if (experiencia.getDemissao() != null) {
                        document.add(new Paragraph(new Text("Demissão: ").setFont(boldFont))
                                .add(new Text(experiencia.getDemissao().toString()).setFont(regularFont)));
                    }
                }
            }

            // Adicionar resumo
            if (candidato.getResumo() != null && !candidato.getResumo().isEmpty()) {
                document.add(new Paragraph(new Text("\nResumo:").setFont(boldFont).setFontSize(18)));
                document.add(new Paragraph(new Text(candidato.getResumo()).setFont(regularFont)));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erro ao gerar PDF", e);
            return null;
        }
    }

    private String parseDate(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(formatter);
    }

}
