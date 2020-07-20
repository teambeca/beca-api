package com.r00t.becaapi.controllers.pri;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.QuestionResponseCredentials;
import com.r00t.becaapi.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<?> getRandomQuestionByRandomType() {
        return ResponseEntity.ok().body(
                questionService.getRandomCredentials());
    }

    @GetMapping("/by/type/{questionType}")
    public ResponseEntity<?> getRandomQuestionByType(
            @PathVariable String questionType) {
        return ResponseEntity.ok().body(
                questionService.getRandomCredentialsByQuestionTyepe(questionType));
    }

    @PostMapping
    public ResponseEntity<?> responseQuestion(
            @RequestBody QuestionResponseCredentials requestCredentials) throws NotFoundException {
        return ResponseEntity.ok().body(
                questionService.insertResponseCredentials(requestCredentials));
    }
}
