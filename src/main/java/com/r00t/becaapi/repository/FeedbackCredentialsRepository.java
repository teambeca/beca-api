package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.FeedbackCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackCredentialsRepository extends MongoRepository<FeedbackCredentials, String> {
}
