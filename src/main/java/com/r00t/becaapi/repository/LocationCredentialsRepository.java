package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.LocationCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCredentialsRepository extends MongoRepository<LocationCredentials, String> {
}
