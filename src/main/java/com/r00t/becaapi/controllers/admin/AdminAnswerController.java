package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.converters.PageConverter;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.AnswerCredentials;
import com.r00t.becaapi.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/question-response")
public class AdminAnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private PageConverter pageConverter;

    @GetMapping("/{page}")
    public ResponseEntity<?> getQuestionResponseCredentials(
            @PathVariable int page) {
        return ResponseEntity.ok().body(
                pageConverter.convertPageToMap(
                        answerService.getCredentials(page)));
    }

    @GetMapping("/by/id/{responseId}")
    public ResponseEntity<?> getQuestionResponseCredentialsById(
            @PathVariable String responseId) throws NotFoundException {
        return ResponseEntity.ok().body(
                answerService.getCredentialsById(responseId));
    }

    @GetMapping("/by/user/id/{userId}")
    public ResponseEntity<?> getQuestionResponseCredentialsByUserId(
            @PathVariable String userId) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                answerService.getCredentialsByUserId(userId));
    }

    @GetMapping("/by/question/id/{questionId}")
    public ResponseEntity<?> getQuestionResponseCredentialsByQuestionId(
            @PathVariable String questionId) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                answerService.getCredentialsByQuestionId(questionId));
    }

    @GetMapping("/by/question/type/{questionType}")
    public ResponseEntity<?> getQuestionResponseCredentialsByQuestionType(
            @PathVariable int questionType) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                answerService.getCredentialsByQuestionType(questionType));
    }

    @GetMapping("/by/creation-date/greater-than/{creationDate}")
    public ResponseEntity<?> getQuestionResponseCredentialsByCreationDateGreaterThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                answerService.getCredentialsByCreationDateGreaterThan(creationDate));
    }

    @GetMapping("/by/creation-date/less-than/{creationDate}")
    public ResponseEntity<?> getQuestionResponseCredentialsByCreationDateLessThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                answerService.getCredentialsByCreationDateLessThan(creationDate));
    }

    @GetMapping("/by/creation-date/greater-than/{start}/and/less-than/{end}")
    public ResponseEntity<?> getQuestionResponseCredentialsByCreationDateBetween(
            @PathVariable long start,
            @PathVariable long end) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                answerService.getCredentialsByCreationDateBetween(start, end));
    }

    @PatchMapping
    public ResponseEntity<?> getQuestionResponseCredentials(
            @RequestBody AnswerCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                answerService.updateCredentials(requestCredentials));
    }

    @DeleteMapping("/{responseId}")
    public ResponseEntity<?> getQuestionResponseCredentials(
            @PathVariable String responseId) {
        answerService.deleteCredentials(responseId);
        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
    }
}
