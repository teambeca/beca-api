package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.QuestionCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionCredentialsRepository extends MongoRepository<QuestionCredentials, String> {

    Optional<Long> countByQuestionType(int questionType);

    Optional<Long> countByNumberOfReplies(int numberOfReplies);
}
