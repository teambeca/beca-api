package com.r00t.becaapi.services;

import com.r00t.becaapi.models.FeedbackCredentials;
import com.r00t.becaapi.repository.FeedbackCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackCredentialsRepository feedbackCredentialsRepository;

    public FeedbackCredentials insertFeedback(String userId, String type, String message) {
        FeedbackCredentials f = new FeedbackCredentials();
        f.setUserId(userId);
        f.setType(type);
        f.setMessage(message);
        f.setCreationDate(System.currentTimeMillis());
        return feedbackCredentialsRepository.insert(f);
    }
}
