package com.sobei.banco_de_talentos.infra.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerImpl {

    @Bean
    public OpenAPI documentation() {
        return new OpenAPI()
                .info(this.info())
                .servers(List.of(this.serverLocal(), this.serverProduction()))
                .components(components());
    }

    private Info info() {
        return new Info()
                .title("Banco de Talentos")
                .description("""
                        Este é um sistema para um banco de talentos onde é possível cadastrar candidatos que desejam
                        participar do processo seletivo da SOBEI.
                        """)
                .summary("API de banco de talentos")
                .version("1.0")
                .contact(this.contact());
    }

    private Contact contact() {
        return new Contact()
                .name("Lucas de Morais Nascimento Taguchi")
                .email("luksmnt1101@gmail.com")
                .url("https://lmdeveloper.com/");
    }

    private Server serverLocal() {
        return new Server()
                .url("http://localhost:8080")
                .description("Servidor local");
    }

    private Server serverProduction() {
        return new Server()
                .url("https://talentpool-production-91e8.up.railway.app")
                .description("Servidor de produção");
    }

    private Components components(){
        return new Components()
                .addSecuritySchemes("bearer",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }

}
