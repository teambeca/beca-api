package com.r00t.becaapi.controllers.pri;

import com.r00t.becaapi.models.requests.MessageRequestCredentials;
import com.r00t.becaapi.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> addFeedback(
            Authentication authentication,
            @Valid @RequestBody MessageRequestCredentials requestCredentials) {
        String userId = (String) authentication.getCredentials();
        return ResponseEntity.ok().body(
                feedbackService.insertFeedback(userId, "feedback", requestCredentials.getMessage()));
    }

    @PostMapping("/report/question")
    public ResponseEntity<?> reportQuestion(
            Authentication authentication,
            @RequestBody MessageRequestCredentials requestCredentials) {
        String userId = (String) authentication.getCredentials();
        return ResponseEntity.ok().body(
                feedbackService.insertFeedback(userId, "report", requestCredentials.getMessage()));
    }
}
