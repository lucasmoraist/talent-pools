package com.sobei.banco_de_talentos.domain.model;

import com.sobei.banco_de_talentos.domain.dto.CandidateRequest;
import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.EstadoCivilEnum;
import com.sobei.banco_de_talentos.domain.enums.SexoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
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

    public Candidato(CandidateRequest request) {
        this.nome = request.nome();
        this.dataNascimento = LocalDate.parse(request.dataNascimento());
        this.idade = request.idade();
        this.sexo = SexoEnum.valueOf(request.sexo());
        this.estadoCivil = EstadoCivilEnum.valueOf(request.estadoCivil());
        this.contato = new Contato(String.valueOf(request.celular()), String.valueOf(request.telefone()), request.email());
        this.endereco = new Endereco(request.regiao(), request.rua(), request.bairro(), request.cep());
        this.documentos = new Documentos(String.valueOf(request.rg()), request.orgaoEmissor(), String.valueOf(request.cpf()), String.valueOf(request.carteiraTrabalho()), request.serie(), String.valueOf(request.tituloEleitor()), request.zonaSecaoUF(), String.valueOf(request.pis()), request.documentosIdentificados().equals("Sim"));
        this.filhos = new Filhos(request.temFilhos().equals("Sim"), request.quantidade());
        this.escolaridades = List.of(new Escolaridade(request.grau(), request.formacao(), request.anoConclusao(), request.instituicao(), request.semestre()), new Escolaridade(request.grau(), request.formacao(), request.cursosComplementaresAnoConclusao(), request.instituicao(), request.semestre()));
        this.experienciaProfissional = List.of(new ExperienciaProfissional(request.empresa1(), request.cargo1(), request.atividades1(), LocalDate.parse(request.admissao1()), LocalDate.parse(request.demissao1()), request.motivo1()), new ExperienciaProfissional(request.empresa2(), request.cargo2(), request.atividades2(), LocalDate.parse(request.admissao2()), LocalDate.parse(request.demissao2()), request.motivo2()), new ExperienciaProfissional(request.empresa3(), request.cargo3(), request.atividades3(), LocalDate.parse(request.admissao3()), LocalDate.parse(request.demissao3()), request.motivo3()));
        this.resumo = request.resumo();
        this.cargo = CargoEnum.valueOf(request.cargo());
        this.status = StatusEnum.PENDENTE;
        this.dataCadastro = LocalDate.parse(request.dataCadastro());
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
