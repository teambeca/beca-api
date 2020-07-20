package com.r00t.becaapi.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Component;

@Component
public class RandomQuestionCredentialsRepository {
    @Value("${question.max.number.of.replies:10}")
    private int maxNumberOfReplies;

    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> T getRandomDocument(Class<T> type, String questionType) {
        TypedAggregation<T> typedAggr;
        if (questionType == null)
            typedAggr = Aggregation.newAggregation(type,
                    context -> new Document("$match", new Document("numberOfReplies", new Document("$lt", maxNumberOfReplies))),
                    context -> new Document("$sample", new Document("size", 1))
            );
        else
            typedAggr = Aggregation.newAggregation(type,
                    context -> new Document("$match", new Document("questionType", questionType)),
                    context -> new Document("$match", new Document("numberOfReplies", new Document("$lt", maxNumberOfReplies))),
                    context -> new Document("$sample", new Document("size", 1))
            );

        AggregationResults<T> aggregationResults = mongoTemplate.aggregate(typedAggr, type);
        return aggregationResults.getMappedResults().get(0);
    }
}
