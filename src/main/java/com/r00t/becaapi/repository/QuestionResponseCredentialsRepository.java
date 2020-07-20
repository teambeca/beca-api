package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.QuestionResponseCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionResponseCredentialsRepository extends MongoRepository<QuestionResponseCredentials, String> {
}
