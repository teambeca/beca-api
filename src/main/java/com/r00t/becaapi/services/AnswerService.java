package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.AnswerCredentials;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.repository.AnswerCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private AnswerCredentialsRepository answerCredentialsRepository;

    public Page<AnswerCredentials> getCredentials(int page) {
        return answerCredentialsRepository.findAll(PageRequest.of(page, 50));
    }

    public AnswerCredentials getCredentialsById(String responseId) throws NotFoundException {
        return answerCredentialsRepository.findById(responseId)
                .orElseThrow(() -> new NotFoundException("questionResponseService.getCredentialsById"));
    }

    public List<AnswerCredentials> getCredentialsByUserId(String userId) throws ServiceUnavailableException {
        return answerCredentialsRepository.findAllByUserId(userId)
                .orElseThrow(() -> new ServiceUnavailableException("questionResponseService.getCredentialsByUserId"));
    }

    public List<AnswerCredentials> getCredentialsByQuestionId(String questionId) throws ServiceUnavailableException {
        return answerCredentialsRepository.findAllByQuestionId(questionId)
                .orElseThrow(() -> new ServiceUnavailableException("questionResponseService.getCredentialsByQuestionId"));
    }

    public List<AnswerCredentials> getCredentialsByQuestionType(int questionType) throws ServiceUnavailableException {
        return answerCredentialsRepository.findAllByType(questionType)
                .orElseThrow(() -> new ServiceUnavailableException("questionResponseService.getCredentialsByQuestionType"));
    }

    public List<AnswerCredentials> getCredentialsByCreationDateGreaterThan(long creationDate) throws ServiceUnavailableException {
        return answerCredentialsRepository.findAllByCreationDateGreaterThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException("questionResponseService.getCredentialsByCreationDateGreaterThan"));
    }

    public List<AnswerCredentials> getCredentialsByCreationDateLessThan(long creationDate) throws ServiceUnavailableException {
        return answerCredentialsRepository.findAllByCreationDateLessThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException("questionResponseService.getCredentialsByCreationDateLessThan"));
    }

    public List<AnswerCredentials> getCredentialsByCreationDateBetween(long start, long end) throws ServiceUnavailableException {
        return answerCredentialsRepository.findAllByCreationDateBetween(start, end)
                .orElseThrow(() -> new ServiceUnavailableException("questionResponseService.getCredentialsByCreationDateBetween"));
    }

    public AnswerCredentials insertCredentials(String userId, AnswerCredentials requestCredentials) throws NotFoundException {
        requestCredentials.setId(null);
        requestCredentials.setUserId(userId);
        requestCredentials.setCreationDate(System.currentTimeMillis());
        requestCredentials = answerCredentialsRepository.insert(requestCredentials);

        if (requestCredentials.getQuestionId() != null)
            if (mongoTemplate.findAndModify(
                    new Query(Criteria.where("id").is(requestCredentials.getQuestionId())),
                    new Update().inc("numberOfReplies", 1),
                    QuestionCredentials.class) == null)
                throw new NotFoundException("questionService.insertResponseCredentials.questionCredentials");

        int score = 0;
        if (requestCredentials.getType() == 0)
            score = requestCredentials.getAnswer().size() * 3;
        else if (requestCredentials.getType() == 1 || requestCredentials.getType() == 2)
            score = 5;
        else if (requestCredentials.getType() == 3)
            score = 10;

        if (mongoTemplate.findAndModify(
                new Query(Criteria.where("id").is(userId)),
                new Update().inc("score", score),
                UserLoginCredentials.class) == null)
            throw new NotFoundException("questionService.insertResponseCredentials.userLoginCredentials");

        return requestCredentials;
    }

    public AnswerCredentials updateCredentials(AnswerCredentials requestCredentials) {
        return answerCredentialsRepository.save(requestCredentials);
    }

    public void deleteCredentials(String responseId) {
        answerCredentialsRepository.deleteById(responseId);
    }
}
