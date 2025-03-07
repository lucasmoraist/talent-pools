package com.sobei.banco_de_talentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BancoDeTalentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoDeTalentosApplication.class, args);
	}

}
