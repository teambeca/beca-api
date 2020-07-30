package com.r00t.becaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@SpringBootApplication
public class BecaApiApplication {

    @Bean
    public Random getRandom() {
        return new Random();
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BecaApiApplication.class, args);
    }
}
