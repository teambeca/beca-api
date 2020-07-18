package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.UserLoginCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginCredentialsRepository extends MongoRepository<UserLoginCredentials, String> {

    Optional<UserLoginCredentials> findByUsername(String username);
}
