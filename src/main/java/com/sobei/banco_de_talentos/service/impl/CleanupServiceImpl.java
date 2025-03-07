package com.sobei.banco_de_talentos.service.impl;

import com.sobei.banco_de_talentos.service.CleanupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class CleanupServiceImpl implements CleanupService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Scheduled(cron = "0 0 0 1 */6 *")
    @Override
    public void deleteOldRecords() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        Date sixMonthAgo = calendar.getTime();

        Query query = new Query(Criteria.where("dataCadastro").lt(sixMonthAgo));
        mongoTemplate.remove(query, "candidatos");

        log.info("Registros antigos removidos");
    }
}
