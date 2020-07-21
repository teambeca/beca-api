package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.models.QuestionResponseCredentials;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.repository.QuestionResponseCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class QuestionResponseService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private QuestionResponseCredentialsRepository questionResponseCredentialsRepository;

    public QuestionResponseCredentials insertCredentials(String userId, QuestionResponseCredentials requestCredentials) throws NotFoundException {
        requestCredentials.setId(null);
        requestCredentials.setUserId(userId);
        requestCredentials.setCreationDate(System.currentTimeMillis());
        requestCredentials = questionResponseCredentialsRepository.insert(requestCredentials);

        if (mongoTemplate.findAndModify(
                new Query(Criteria.where("id").is(requestCredentials.getQuestionId())),
                new Update().inc("numberOfReplies", 1),
                QuestionCredentials.class) == null)
            throw new NotFoundException("questionService.insertResponseCredentials.questionCredentials");

        if (mongoTemplate.findAndModify(
                new Query(Criteria.where("id").is(userId)),
                new Update().inc("score", 10),
                UserLoginCredentials.class) == null)
            throw new NotFoundException("questionService.insertResponseCredentials.userLoginCredentials");

        return requestCredentials;
    }
}
