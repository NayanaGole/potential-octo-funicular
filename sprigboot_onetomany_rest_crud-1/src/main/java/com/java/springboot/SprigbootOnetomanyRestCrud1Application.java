package com.java.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SprigbootOnetomanyRestCrud1Application {
    public static void main(String[] args) {
        SpringApplication.run(SprigbootOnetomanyRestCrud1Application.class, args);
    }
}