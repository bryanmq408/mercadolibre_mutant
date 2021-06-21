package com.meli.mutants.mutant.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {

    private static final String TITLE = "Mutant Rest API";
    private static final String DESCRIPTION = "Api designed to identify if a DNA sequence is mutant";
    private static final String VERSION = "Beta 0.0.1";
    private static final String AUTHOR = "Brayan Alberto Morales Quintero";
    private static final String EMAIL = "Brayan Alberto Morales Quintero";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiDetails())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.meli.mutants.mutant.application.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiDetails() {
        return new ApiInfo(
                TITLE,
                DESCRIPTION,
                VERSION,
                "",
                new Contact(AUTHOR, "", EMAIL),
                "",
                "",
                Collections.emptyList());
    }
}