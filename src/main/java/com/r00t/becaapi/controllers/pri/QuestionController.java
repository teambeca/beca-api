package com.r00t.becaapi.controllers.pri;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.AnswerCredentials;
import com.r00t.becaapi.services.AnswerService;
import com.r00t.becaapi.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @GetMapping
    public ResponseEntity<?> getRandomQuestionByRandomType() throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionService.getRandomCredentials());
    }

    @GetMapping("/by/type/{questionType}")
    public ResponseEntity<?> getRandomQuestionByType(
            @PathVariable int questionType) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionService.getRandomCredentialsByType(questionType));
    }

    @PostMapping
    public ResponseEntity<?> answerQuestion(
            Authentication authentication,
            @RequestBody AnswerCredentials requestCredentials) throws NotFoundException {
        String userId = (String) authentication.getCredentials();
        return ResponseEntity.ok().body(
                answerService.insertCredentials(userId, requestCredentials));
    }
}
