package com.sobei.banco_de_talentos.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "candidatos")
public class Candidato {

    @Id
    private String id;
    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;
    @NotBlank(message = "O campo data de nascimento é obrigatório")
    private String dataNascimento;
    @NotNull(message = "O campo idade é obrigatório")
    @Min(value = 18, message = "A idade mínima é 18 anos")
    private int idade;
    @NotBlank(message = "O campo sexo é obrigatório")
    private String sexo;
    @NotBlank(message = "O campo estado civil é obrigatório")
    private String estadoCivil;
    private Contato contato;
    private Endereco endereco;
    private Documentos documentos;
    private Filhos filhos;
    private Escolaridade escolaridade;
    private List<ExperienciaProfissional> experienciaProfissional;
    private String resumo;
    private Boolean isAccepted;

    public Candidato(Candidato request) {
        this.nome = request.getNome();
        this.dataNascimento = request.getDataNascimento();
        this.idade = request.getIdade();
        this.sexo = request.getSexo();
        this.estadoCivil = request.getEstadoCivil();
        this.contato = request.getContato();
        this.endereco = request.getEndereco();
        this.documentos = request.getDocumentos();
        this.filhos = request.getFilhos();
        this.escolaridade = request.getEscolaridade();
        this.experienciaProfissional = request.getExperienciaProfissional();
        this.resumo = request.getResumo();
        this.isAccepted = false;
    }
}
