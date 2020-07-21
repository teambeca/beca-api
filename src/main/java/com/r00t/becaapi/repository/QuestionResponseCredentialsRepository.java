package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.QuestionResponseCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionResponseCredentialsRepository extends MongoRepository<QuestionResponseCredentials, String> {

    Optional<List<QuestionResponseCredentials>> findAllByUserId(String userId);

    Optional<List<QuestionResponseCredentials>> findAllByQuestionId(String questionId);

    Optional<List<QuestionResponseCredentials>> findAllByQuestionType(int questionType);

    Optional<List<QuestionResponseCredentials>> findAllByCreationDateGreaterThan(long creationDate);

    Optional<List<QuestionResponseCredentials>> findAllByCreationDateLessThan(long creationDate);

    Optional<List<QuestionResponseCredentials>> findAllByCreationDateBetween(long start, long end);
}
