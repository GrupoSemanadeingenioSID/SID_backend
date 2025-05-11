package com.sid.portal_web.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.sid.portal_web") // Asegúrate de que coincida con tu paquete base
                .pathsToMatch("/**")
                .build();
    }

}
