package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/question")
public class AdminQuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/by/id/{questionId}")
    public ResponseEntity<?> getQuestionCredentialsById(
            @PathVariable String questionId) throws NotFoundException {
        return ResponseEntity.ok().body(
                questionService.getCredentialsById(questionId));
    }

    @GetMapping("/count/by/question-type/{questionType}")
    public ResponseEntity<?> getQuestionCredentialsByQuestionType(
            @PathVariable int questionType) {
        return ResponseEntity.ok().body(
                questionService.countCredentialsByQuestionType(questionType));
    }

    @GetMapping("/count/by/number-of-replies/{numberOfReplies}")
    public ResponseEntity<?> getQuestionCredentialsByNumberOfReplies(
            @PathVariable int numberOfReplies) {
        return ResponseEntity.ok().body(
                questionService.countCredentialsByNumberOfReplies(numberOfReplies));
    }

    @PatchMapping
    public ResponseEntity<?> updateQuestionCredentials(
            @RequestBody QuestionCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                questionService.updateCredentials(requestCredentials));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestionCredentials(
            @PathVariable String questionId) {
        questionService.deleteCredentials(questionId);
        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
    }
}
