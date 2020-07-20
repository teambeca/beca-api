package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.QuestionCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCredentialsRepository extends MongoRepository<QuestionCredentials, String> {
}
