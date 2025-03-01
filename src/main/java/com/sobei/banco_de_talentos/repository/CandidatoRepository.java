package com.sobei.banco_de_talentos.repository;

import com.sobei.banco_de_talentos.domain.model.Candidato;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidatoRepository extends MongoRepository<Candidato, String> {
}
