package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.models.QuestionResponseCredentials;
import com.r00t.becaapi.repository.QuestionCredentialsRepository;
import com.r00t.becaapi.repository.QuestionResponseCredentialsRepository;
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
    @Autowired
    private QuestionResponseCredentialsRepository questionResponseCredentialsRepository;

    public QuestionCredentials getCredentialsById(String id) throws NotFoundException {
        return questionCredentialsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(""));
    }

    public QuestionCredentials getRandomCredentials() {
        return aggregateCredentials(Aggregation.newAggregation(
                QuestionCredentials.class,
                context -> new Document("$match", new Document("numberOfReplies", new Document("$lt", maxNumberOfReplies))),
                context -> new Document("$sample", new Document("size", 1))
        ));
    }

    public QuestionCredentials getRandomCredentialsByQuestionTyepe(String questionType) {
        return aggregateCredentials(Aggregation.newAggregation(
                QuestionCredentials.class,
                context -> new Document("$match", new Document("questionType", questionType)),
                context -> new Document("$match", new Document("numberOfReplies", new Document("$lt", maxNumberOfReplies))),
                context -> new Document("$sample", new Document("size", 1))
        ));
    }

    public QuestionResponseCredentials insertResponseCredentials(QuestionResponseCredentials requestCredentials) throws NotFoundException {
        QuestionCredentials questionCredentials = getCredentialsById(requestCredentials.getQuestionId());

        requestCredentials.setId(null);
        requestCredentials.setCreationDate(System.currentTimeMillis());
        requestCredentials = questionResponseCredentialsRepository.insert(requestCredentials);

        questionCredentials.setNumberOfReplies(
                questionCredentials.getNumberOfReplies() + 1);
        questionCredentialsRepository.save(questionCredentials);
        return requestCredentials;
    }

    private QuestionCredentials aggregateCredentials(TypedAggregation<QuestionCredentials> typedAggr) {
        return mongoTemplate.aggregate(typedAggr, QuestionCredentials.class)
                .getMappedResults()
                .get(0);
    }
}
