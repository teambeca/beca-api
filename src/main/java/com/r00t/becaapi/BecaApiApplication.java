package com.r00t.becaapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BecaApiApplication implements CommandLineRunner {
    @Autowired
    private UserLoginCredentialsRepository repository;

    public static void main(String[] args) {

        SpringApplication.run(BecaApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserLoginCredentials userLoginCredentials = new UserLoginCredentials();
        userLoginCredentials.setName("test");
        repository.insert(userLoginCredentials);
    }
}
