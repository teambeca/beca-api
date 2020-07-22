package com.r00t.becaapi.services;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.models.QuestionResponseCredentials;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.repository.QuestionResponseCredentialsRepository;
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
public class QuestionResponseService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private QuestionResponseCredentialsRepository questionResponseCredentialsRepository;

    public Page<QuestionResponseCredentials> getCredentials(int page) {
        return questionResponseCredentialsRepository.findAll(PageRequest.of(page, 50));
    }

    public QuestionResponseCredentials getCredentialsById(String responseId) throws NotFoundException {
        return questionResponseCredentialsRepository.findById(responseId)
                .orElseThrow(() -> new NotFoundException(""));
    }

    public List<QuestionResponseCredentials> getCredentialsByUserId(String userId) throws ServiceUnavailableException {
        return questionResponseCredentialsRepository.findAllByUserId(userId)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

    public List<QuestionResponseCredentials> getCredentialsByQuestionId(String questionId) throws ServiceUnavailableException {
        return questionResponseCredentialsRepository.findAllByQuestionId(questionId)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

    public List<QuestionResponseCredentials> getCredentialsByQuestionType(int questionType) throws ServiceUnavailableException {
        return questionResponseCredentialsRepository.findAllByQuestionType(questionType)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

    public List<QuestionResponseCredentials> getCredentialsByCreationDateGreaterThan(long creationDate) throws ServiceUnavailableException {
        return questionResponseCredentialsRepository.findAllByCreationDateGreaterThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

    public List<QuestionResponseCredentials> getCredentialsByCreationDateLessThan(long creationDate) throws ServiceUnavailableException {
        return questionResponseCredentialsRepository.findAllByCreationDateLessThan(creationDate)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

    public List<QuestionResponseCredentials> getCredentialsByCreationDateBetween(long start, long end) throws ServiceUnavailableException {
        return questionResponseCredentialsRepository.findAllByCreationDateBetween(start, end)
                .orElseThrow(() -> new ServiceUnavailableException(""));
    }

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

    public QuestionResponseCredentials updateCredentials(QuestionResponseCredentials requestCredentials) {
        return questionResponseCredentialsRepository.save(requestCredentials);
    }

    public void deleteCredentials(String responseId) {
        questionResponseCredentialsRepository.deleteById(responseId);
    }
}
