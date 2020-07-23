package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.models.responses.QuestionResponseCredentials;
import com.r00t.becaapi.repository.QuestionCredentialsRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Value("${question.max.number.of.replies:10}")
    private int maxNumberOfReplies;
    @Autowired
    private Random random;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private QuestionCredentialsRepository questionCredentialsRepository;

    public Page<QuestionCredentials> getCredentials(int page) {
        return questionCredentialsRepository.findAll(PageRequest.of(page, 50));
    }

    public long countCredentialsByQuestionType(String type) throws ServiceUnavailableException {
        return questionCredentialsRepository.countAllByType(type)
                .orElseThrow(() -> new ServiceUnavailableException("questionService.countCredentialsByQuestionType"));
    }

    public long countCredentialsByNumberOfRepliesBetween(int start, int end) throws ServiceUnavailableException {
        return questionCredentialsRepository.countAllByNumberOfRepliesBetween(start, end)
                .orElseThrow(() -> new ServiceUnavailableException("questionService.countCredentialsByNumberOfRepliesBetween"));
    }

    public QuestionCredentials getCredentialsById(String questionId) throws NotFoundException {
        return questionCredentialsRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("questionService.getCredentialsById"));
    }

    public QuestionResponseCredentials getRandomCredentials() throws ServiceUnavailableException {
        int randomType = random.nextInt(4);
        return getRandomCredentialsByType(randomType);
    }

    public QuestionResponseCredentials getRandomCredentialsByType(int type) throws ServiceUnavailableException {
        if (type < 3) {
            QuestionCredentials questionCredentials = getRandomCredentialsByTypeAndSize(
                    (type == 0 || type == 1) ? "sentence" : "word", 1).get(0);

            QuestionResponseCredentials responseCredentials = new QuestionResponseCredentials();
            responseCredentials.setId(questionCredentials.getId());
            responseCredentials.setText(questionCredentials.getText());
            responseCredentials.setType(type);
            return responseCredentials;

            /*return getRandomCredentialsByTypeAndSize(
                    (type == 0 || type == 1) ? "sentence" : "word", 1)
                    .stream()
                    .map(q -> {
                        QuestionResponseCredentials r = new QuestionResponseCredentials();
                        r.setId(q.getId());
                        r.setText(q.getText());
                        r.setType(type);
                        return r;
                    }).findFirst()
                    .orElseThrow(() -> new ServiceUnavailableException(""));*/
        } else if (type == 3) {
            QuestionResponseCredentials questionResponseCredentials = new QuestionResponseCredentials();
            questionResponseCredentials.setText(
                    getRandomCredentialsByTypeAndSize("word", 2)
                            .stream()
                            .map(QuestionCredentials::getText)
                            .map(l -> l.get(0))
                            .collect(Collectors.toList()));
            questionResponseCredentials.setType(type);
            return questionResponseCredentials;
        } else
            throw new ServiceUnavailableException("questionService.getRandomCredentialsByType");
    }

    public List<QuestionCredentials> getRandomCredentialsByTypeAndSize(String type, int size) {
        return aggregateCredentials(Aggregation.newAggregation(
                QuestionCredentials.class,
                context -> new Document("$match", new Document("type", type)),
                context -> new Document("$match", new Document("numberOfReplies", new Document("$lt", maxNumberOfReplies))),
                context -> new Document("$sample", new Document("size", size))
        ));
    }

    public QuestionCredentials insertCredentials(QuestionCredentials requestCredentials) {
        requestCredentials.setId(null);
        requestCredentials.setNumberOfReplies(0);
        return questionCredentialsRepository.insert(requestCredentials);
    }

    public QuestionCredentials updateCredentials(QuestionCredentials requestCredentials) {
        return questionCredentialsRepository.save(requestCredentials);
    }

    public void deleteCredentials(String questionId) {
        questionCredentialsRepository.deleteById(questionId);
    }

    private List<QuestionCredentials> aggregateCredentials(TypedAggregation<QuestionCredentials> typedAggr) {
        return mongoTemplate.aggregate(typedAggr, QuestionCredentials.class)
                .getMappedResults();
    }
}
