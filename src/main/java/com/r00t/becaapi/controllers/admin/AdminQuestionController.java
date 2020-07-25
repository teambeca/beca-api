package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.converters.PageConverter;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.QuestionCredentials;
import com.r00t.becaapi.models.requests.Seed;
import com.r00t.becaapi.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/admin/question")
public class AdminQuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PageConverter pageConverter;

    @GetMapping("/{page}")
    public ResponseEntity<?> getQuestionCredentials(
            @PathVariable int page) {
        return ResponseEntity.ok().body(
                pageConverter.convertPageToMap(
                        questionService.getCredentials(page)));
    }

    @GetMapping("/by/id/{questionId}")
    public ResponseEntity<?> getQuestionCredentialsById(
            @PathVariable String questionId) throws NotFoundException {
        return ResponseEntity.ok().body(
                questionService.getCredentialsById(questionId));
    }

    @GetMapping("/count/by/question-type/{questionType}")
    public ResponseEntity<?> getQuestionCredentialsByQuestionType(
            @PathVariable String questionType) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionService.countCredentialsByQuestionType(questionType));
    }

    @GetMapping("/count/by/number-of-replies/greater-than/{start}/and/less-than/{end}")
    public ResponseEntity<?> getQuestionCredentialsByNumberOfReplies(
            @PathVariable int start,
            @PathVariable int end) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionService.countCredentialsByNumberOfRepliesBetween(start, end));
    }

    @PostMapping
    public ResponseEntity<?> insertQuestionCredentials(
            @RequestBody QuestionCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                questionService.insertCredentials(requestCredentials));
    }

    @PostMapping("/seed")
    public ResponseEntity<?> seed(
            @RequestBody Seed seed) {
        seed.getTexts()
                .forEach(s -> {
                    QuestionCredentials q = new QuestionCredentials();
                    q.setType(seed.getType());
                    if (seed.getType().equals("sentence"))
                        q.setText(Arrays.asList(s.split(" ")));
                    else
                        q.setText(Collections.singletonList(s));
                    questionService.insertCredentials(q);
                });

        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
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
