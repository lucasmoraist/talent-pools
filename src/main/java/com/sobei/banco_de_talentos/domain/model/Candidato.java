package com.sobei.banco_de_talentos.domain.model;

import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.EstadoCivilEnum;
import com.sobei.banco_de_talentos.domain.enums.SexoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    @NotNull(message = "O campo data de nascimento é obrigatório")
    private LocalDate dataNascimento;
    private int idade;
    @NotNull(message = "O campo sexo é obrigatório")
    private SexoEnum sexo;
    @NotNull(message = "O campo estado civil é obrigatório")
    private EstadoCivilEnum estadoCivil;
    private Contato contato;
    private Endereco endereco;
    private Documentos documentos;
    private Filhos filhos;
    private List<Escolaridade> escolaridades;
    private List<ExperienciaProfissional> experienciaProfissional;
    private String resumo;
    private CargoEnum cargo;
    private StatusEnum status;
    private LocalDate dataCadastro;

    public Candidato(Candidato request, CargoEnum cargo) {
        this.nome = request.getNome();
        this.dataNascimento = request.getDataNascimento();
        this.idade = LocalDate.now().getYear() - request.getDataNascimento().getYear();
        this.sexo = request.getSexo();
        this.estadoCivil = request.getEstadoCivil();
        this.contato = request.getContato();
        this.endereco = request.getEndereco();
        this.documentos = request.getDocumentos();
        this.filhos = request.getFilhos();
        this.escolaridades = request.getEscolaridades();
        this.experienciaProfissional = request.getExperienciaProfissional();
        this.resumo = request.getResumo();
        this.cargo = cargo;
        this.status = StatusEnum.PENDENTE;
        this.dataCadastro = LocalDate.now();
    }

    public void setStatusPendente() {
        this.status = StatusEnum.PENDENTE;
    }

    public void setStatusEmAnalise() {
        this.status = StatusEnum.EM_ANALISE;
    }

    public void setStatusAprovado() {
        this.status = StatusEnum.APROVADO;
    }
}
