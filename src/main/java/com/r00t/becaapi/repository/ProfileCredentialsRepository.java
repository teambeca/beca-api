package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.ProfileCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileCredentialsRepository extends MongoRepository<ProfileCredentials, String> {

    Optional<ProfileCredentials> findByUserId(String userId);
}
