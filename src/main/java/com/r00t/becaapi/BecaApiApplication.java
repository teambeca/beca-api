package com.r00t.becaapi;

import com.r00t.becaapi.repository.UserLoginCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class BecaApiApplication implements CommandLineRunner {
    @Autowired
    private Random random;
    @Autowired
    private UserLoginCredentialsRepository repository;

    @Bean
    public Random getRandom() {
        return new Random();
    }

    public static void main(String[] args) {
        SpringApplication.run(BecaApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
