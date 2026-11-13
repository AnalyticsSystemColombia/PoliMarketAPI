package com.polimarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.polimarket")
@EnableJpaRepositories(basePackages = "com.polimarket.repositorio")
@EntityScan(basePackages = "com.polimarket.modelo")
public class PoliMarketApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoliMarketApiApplication.class, args);
    }
}