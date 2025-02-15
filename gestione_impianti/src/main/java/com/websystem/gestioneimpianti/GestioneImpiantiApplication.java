package com.websystem.gestioneimpianti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})

public class GestioneImpiantiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestioneImpiantiApplication.class, args);
    }
}
