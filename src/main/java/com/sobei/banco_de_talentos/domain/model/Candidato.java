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
import java.time.format.DateTimeFormatter;
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

    public Candidato(CandidateRequest request) {
        this.nome = request.nome();
        this.dataNascimento = parseDate(request.dataNascimento());
        this.idade = request.idade();
        this.sexo = parseSexo(request.sexo());
        this.estadoCivil = parseEstadoCivil(request.estadoCivil());
        this.contato = new Contato(String.valueOf(request.celular()), String.valueOf(request.telefone()), request.email());
        this.endereco = new Endereco(request.cep(),request.regiao(), request.rua(), request.bairro());
        this.documentos = new Documentos(String.valueOf(request.rg()), request.orgaoEmissor(), String.valueOf(request.cpf()), String.valueOf(request.carteiraTrabalho()), request.serie(), String.valueOf(request.tituloEleitor()), request.zonaSecaoUF(), String.valueOf(request.pis()), request.documentosIdentificados().equals("Sim"));
        this.filhos = new Filhos(request.temFilhos().equals("Sim"), request.quantidade());
        this.escolaridades = List.of(new Escolaridade(request.grau(), request.formacao(), request.anoConclusao(), request.instituicao(), request.semestre()), new Escolaridade(request.grau(), request.formacao(), request.cursosComplementaresAnoConclusao(), request.instituicao(), request.semestre()));
        this.experienciaProfissional = List.of(
                new ExperienciaProfissional(
                        request.empresa1(), request.cargo1(), request.atividades1(),
                        parseDate(request.admissao1()), parseDate(request.demissao1()), request.motivo1()
                ),
                new ExperienciaProfissional(
                        request.empresa2(), request.cargo2(), request.atividades2(),
                        parseDate(request.admissao2()), parseDate(request.demissao2()), request.motivo2()
                ),
                new ExperienciaProfissional(
                        request.empresa3(), request.cargo3(), request.atividades3(),
                        parseDate(request.admissao3()), parseDate(request.demissao3()), request.motivo3()
                ));
        this.resumo = request.resumo();
        this.cargo = parseCargo(request.cargo());
        this.status = StatusEnum.PENDENTE;
        this.dataCadastro = parseDate(request.dataCadastro());
    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    private EstadoCivilEnum parseEstadoCivil(String estadoCivil) {
        if (estadoCivil == null || estadoCivil.isBlank()) {
            return EstadoCivilEnum.OUTRO;
        }
        return EstadoCivilEnum.valueOf(estadoCivil);
    }

    private SexoEnum parseSexo(String sexo) {
        if (sexo == null || sexo.isBlank()) {
            return SexoEnum.OUTRO;
        }
        return SexoEnum.valueOf(sexo);
    }

    private CargoEnum parseCargo(String cargo) {
        if (cargo == null || cargo.isBlank()) {
            return CargoEnum.OUTRO;
        }
        return CargoEnum.valueOf(cargo);
    }
}
