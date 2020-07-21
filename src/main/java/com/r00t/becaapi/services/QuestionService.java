package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.repository.QuestionCredentialsRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Value("${question.max.number.of.replies:10}")
    private int maxNumberOfReplies;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private QuestionCredentialsRepository questionCredentialsRepository;

    public long countCredentialsByQuestionType(int questionType) throws ServiceUnavailableException {
        return questionCredentialsRepository.countByQuestionType(questionType)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

    public long countCredentialsByNumberOfReplies(int numberOfReplies) throws ServiceUnavailableException {
        return questionCredentialsRepository.countByNumberOfReplies(numberOfReplies)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

    public QuestionCredentials getCredentialsById(String questionId) throws NotFoundException {
        return questionCredentialsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException(""));
    }

    public QuestionCredentials getRandomCredentials() {
        return aggregateCredentials(Aggregation.newAggregation(
                QuestionCredentials.class,
                context -> new Document("$match", new Document("numberOfReplies", new Document("$lt", maxNumberOfReplies))),
                context -> new Document("$sample", new Document("size", 1))
        ));
    }

    public QuestionCredentials getRandomCredentialsByQuestionType(int questionType) {
        return aggregateCredentials(Aggregation.newAggregation(
                QuestionCredentials.class,
                context -> new Document("$match", new Document("questionType", questionType)),
                context -> new Document("$match", new Document("numberOfReplies", new Document("$lt", maxNumberOfReplies))),
                context -> new Document("$sample", new Document("size", 1))
        ));
    }

    public QuestionCredentials insertCredentials(QuestionCredentials requestCredentials) {
        requestCredentials.setId(null);
        requestCredentials.setNumberOfReplies(0);
        requestCredentials.setCreationDate(System.currentTimeMillis());
        return questionCredentialsRepository.insert(requestCredentials);
    }

    public QuestionCredentials updateCredentials(QuestionCredentials requestCredentials) {
        return questionCredentialsRepository.save(requestCredentials);
    }

    public void deleteCredentials(String questionId) {
        questionCredentialsRepository.deleteById(questionId);
    }

    private QuestionCredentials aggregateCredentials(TypedAggregation<QuestionCredentials> typedAggr) {
        return mongoTemplate.aggregate(typedAggr, QuestionCredentials.class)
                .getMappedResults()
                .get(0);
    }
}
