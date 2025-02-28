package com.sobei.banco_de_talentos.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import com.sobei.banco_de_talentos.service.CandidatoService;
import com.sobei.banco_de_talentos.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final CandidatoService candidatoService;

    public byte[] generate(String id) {
        Candidato candidato = this.candidatoService.findById(id);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            document.add(new Paragraph(new Text(candidato.getNome()).setFont(boldFont).setFontSize(18)));
            document.add(new Paragraph(new Text("Data de nascimento: ").setFont(boldFont))
                    .add(new Text(candidato.getDataNascimento() != null ? candidato.getDataNascimento().toString() : "").setFont(regularFont)));
            document.add(new Paragraph(new Text("Idade: ").setFont(boldFont))
                    .add(new Text(String.valueOf(candidato.getIdade())).setFont(regularFont)));
            document.add(new Paragraph(new Text("Sexo: ").setFont(boldFont))
                    .add(new Text(candidato.getSexo().getDescricao()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Estado civil: ").setFont(boldFont))
                    .add(new Text(candidato.getEstadoCivil().getDescricao()).setFont(regularFont)));

            document.add(new Paragraph(new Text("\nCargo pretendido:").setFont(boldFont).setFontSize(18)));
            document.add(new Paragraph(new Text(candidato.getCargo().getDescricao()).setFont(regularFont)));

            document.add(new Paragraph(new Text("\nContato:").setFont(boldFont).setFontSize(18)));
            document.add(new Paragraph(new Text("Celular: ").setFont(boldFont))
                    .add(new Text(candidato.getContato().getCelular()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Telefone: ").setFont(boldFont))
                    .add(new Text(candidato.getContato().getTelefone()).setFont(regularFont)));
            document.add(new Paragraph(new Text("E-mail: ").setFont(boldFont))
                    .add(new Text(candidato.getContato().getEmail()).setFont(regularFont)));

            document.add(new Paragraph(new Text("\nEndereço:").setFont(boldFont).setFontSize(18)));
            document.add(new Paragraph(new Text("CEP: ").setFont(boldFont))
                    .add(new Text(candidato.getEndereco().getCep()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Região: ").setFont(boldFont))
                    .add(new Text(candidato.getEndereco().getRegiao()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Rua: ").setFont(boldFont))
                    .add(new Text(candidato.getEndereco().getRua()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Bairro: ").setFont(boldFont))
                    .add(new Text(candidato.getEndereco().getBairro()).setFont(regularFont)));

            document.add(new Paragraph(new Text("\nDocumentos:").setFont(boldFont).setFontSize(18)));
            document.add(new Paragraph(new Text("RG: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getRg()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Orgão emissor: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getOrgaoEmissor()).setFont(regularFont)));
            document.add(new Paragraph(new Text("CPF: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getCpf()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Carteira de trabalho: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getCarteiraTrabalho()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Série: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getSerie()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Título de eleitor: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getTituloEleitor()).setFont(regularFont)));
            document.add(new Paragraph(new Text("Zona e seção eleitoral: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getZonaSecaoUF()).setFont(regularFont)));
            document.add(new Paragraph(new Text("PIS: ").setFont(boldFont))
                    .add(new Text(candidato.getDocumentos().getPis()).setFont(regularFont)));

            if (candidato.getFilhos().isTemFilhos()) {
                document.add(new Paragraph(new Text("\nFilhos:").setFont(boldFont).setFontSize(18)));
                document.add(new Paragraph(new Text("Quantidade de filhos: ").setFont(boldFont))
                        .add(new Text(String.valueOf(candidato.getFilhos().getQuantidade())).setFont(regularFont)));
            }

            if (!candidato.getEscolaridades().isEmpty()) {
                document.add(new Paragraph(new Text("\nEscolaridade:").setFont(boldFont).setFontSize(18)));
                for (int i = 0; i < candidato.getEscolaridades().size(); i++) {
                    document.add(new Paragraph(new Text("Grau: ").setFont(boldFont))
                            .add(new Text(candidato.getEscolaridades().get(i).getGrau()).setFont(regularFont)));
                    document.add(new Paragraph(new Text("Formação: ").setFont(boldFont))
                            .add(new Text(candidato.getEscolaridades().get(i).getFormacao()).setFont(regularFont)));
                    document.add(new Paragraph(new Text("Ano de conclusão: ").setFont(boldFont))
                            .add(new Text(String.valueOf(candidato.getEscolaridades().get(i).getAnoConclusao())).setFont(regularFont)));
                    document.add(new Paragraph(new Text("Instituição: ").setFont(boldFont))
                            .add(new Text(candidato.getEscolaridades().get(i).getInstituicao()).setFont(regularFont)));
                }
            }

            if (!candidato.getExperienciaProfissional().isEmpty()) {
                document.add(new Paragraph(new Text("\nExperiência profissional:").setFont(boldFont).setFontSize(18)));
                for (int i = 0; i < candidato.getExperienciaProfissional().size(); i++) {
                    document.add(new Paragraph(new Text("Empresa: ").setFont(boldFont))
                            .add(new Text(candidato.getExperienciaProfissional().get(i).getEmpresa()).setFont(regularFont)));
                    document.add(new Paragraph(new Text("Cargo: ").setFont(boldFont))
                            .add(new Text(candidato.getExperienciaProfissional().get(i).getCargo()).setFont(regularFont)));
                    document.add(new Paragraph(new Text("Descrição: ").setFont(boldFont))
                            .add(new Text(candidato.getExperienciaProfissional().get(i).getAtividades()).setFont(regularFont)));
                    document.add(new Paragraph(new Text("Admissão: ").setFont(boldFont))
                            .add(new Text(candidato.getExperienciaProfissional().get(i).getAdmissao() != null ? candidato.getExperienciaProfissional().get(i).getAdmissao().toString() : "").setFont(regularFont)));
                    document.add(new Paragraph(new Text("Demissão: ").setFont(boldFont))
                            .add(new Text(candidato.getExperienciaProfissional().get(i).getDemissao() != null ? candidato.getExperienciaProfissional().get(i).getDemissao().toString() : "").setFont(regularFont)));
                }
            }

            document.add(new Paragraph(new Text("\nResumo:").setFont(boldFont).setFontSize(18)));
            document.add(new Paragraph(new Text(candidato.getResumo()).setFont(regularFont)));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erro ao gerar PDF", e);
            return null;
        }
    }
}
