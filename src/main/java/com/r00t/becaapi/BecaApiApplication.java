package com.r00t.becaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class BecaApiApplication {

    @Bean
    public Random getRandom() {
        return new Random();
    }

    public static void main(String[] args) {
        SpringApplication.run(BecaApiApplication.class, args);
    }
}
