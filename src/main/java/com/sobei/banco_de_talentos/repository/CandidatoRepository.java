package com.sobei.banco_de_talentos.repository;

import com.sobei.banco_de_talentos.domain.enums.CargoEnum;
import com.sobei.banco_de_talentos.domain.enums.StatusEnum;
import com.sobei.banco_de_talentos.domain.model.Candidato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CandidatoRepository extends MongoRepository<Candidato, String> {
    @Query("""
        {
            $and: [
                { "cargo": ?0 },
                { $or: [
                    { "status": ?1 },
                    { "?1": null }
                ] },
                { $or: [
                    { "endereco.regiao": { $regex: ?2, $options: 'i' } },
                    { "?2": ".*" },
                    { "endereco.regiao": { $exists: false } }
                ] }
            ]
        }
        """)
    Page<Candidato> findByFilters(CargoEnum cargo, StatusEnum status, String regiao, Pageable pageable);

}
