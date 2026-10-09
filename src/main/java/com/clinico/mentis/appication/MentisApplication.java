package com.clinico.mentis.appication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.clinico.mentis")
@EnableJpaRepositories(basePackages = "com.clinico.mentis.infraestructura.adapter.persistence")
@EntityScan(basePackages = "com.clinico.mentis.infraestructura.adapter.persistence.entity")
public class MentisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentisApplication.class, args);
    }
}
