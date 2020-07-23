package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.QuestionCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionCredentialsRepository extends MongoRepository<QuestionCredentials, String> {

    Optional<Long> countAllByType(String type);

    Optional<Long> countAllByNumberOfRepliesBetween(int start, int end);
}
