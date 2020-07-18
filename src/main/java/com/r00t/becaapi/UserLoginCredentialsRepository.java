package com.r00t.becaapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginCredentialsRepository extends MongoRepository<UserLoginCredentials, String> {
}
