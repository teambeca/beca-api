package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.AnswerCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerCredentialsRepository extends MongoRepository<AnswerCredentials, String> {

    Optional<List<AnswerCredentials>> findAllByUserId(String userId);

    Optional<List<AnswerCredentials>> findAllByQuestionId(String questionId);

    Optional<List<AnswerCredentials>> findAllByType(int type);

    Optional<List<AnswerCredentials>> findAllByCreationDateGreaterThan(long creationDate);

    Optional<List<AnswerCredentials>> findAllByCreationDateLessThan(long creationDate);

    Optional<List<AnswerCredentials>> findAllByCreationDateBetween(long start, long end);
}
