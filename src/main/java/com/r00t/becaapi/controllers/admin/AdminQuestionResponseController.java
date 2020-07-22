package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.converters.PageConverter;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.QuestionResponseCredentials;
import com.r00t.becaapi.services.QuestionResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/question-response")
public class AdminQuestionResponseController {
    @Autowired
    private QuestionResponseService questionResponseService;
    @Autowired
    private PageConverter pageConverter;

    @GetMapping("/{page}")
    public ResponseEntity<?> getQuestionResponseCredentials(
            @PathVariable int page) {
        return ResponseEntity.ok().body(
                pageConverter.convertPageToMap(
                        questionResponseService.getCredentials(page)));
    }

    @GetMapping("/by/id/{responseId}")
    public ResponseEntity<?> getQuestionResponseCredentialsById(
            @PathVariable String responseId) throws NotFoundException {
        return ResponseEntity.ok().body(
                questionResponseService.getCredentialsById(responseId));
    }

    @GetMapping("/by/user/id/{userId}")
    public ResponseEntity<?> getQuestionResponseCredentialsByUserId(
            @PathVariable String userId) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionResponseService.getCredentialsByUserId(userId));
    }

    @GetMapping("/by/question/id/{questionId}")
    public ResponseEntity<?> getQuestionResponseCredentialsByQuestionId(
            @PathVariable String questionId) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionResponseService.getCredentialsByQuestionId(questionId));
    }

    @GetMapping("/by/question/type/{questionType}")
    public ResponseEntity<?> getQuestionResponseCredentialsByQuestionType(
            @PathVariable int questionType) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionResponseService.getCredentialsByQuestionType(questionType));
    }

    @GetMapping("/by/creation-date/greater-than/{creationDate}")
    public ResponseEntity<?> getQuestionResponseCredentialsByCreationDateGreaterThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionResponseService.getCredentialsByCreationDateGreaterThan(creationDate));
    }

    @GetMapping("/by/creation-date/less-than/{creationDate}")
    public ResponseEntity<?> getQuestionResponseCredentialsByCreationDateLessThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionResponseService.getCredentialsByCreationDateLessThan(creationDate));
    }

    @GetMapping("/by/creation-date/greater-than/{start}/and/less-than/{end}")
    public ResponseEntity<?> getQuestionResponseCredentialsByCreationDateBetween(
            @PathVariable long start,
            @PathVariable long end) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                questionResponseService.getCredentialsByCreationDateBetween(start, end));
    }

    @PatchMapping
    public ResponseEntity<?> getQuestionResponseCredentials(
            @RequestBody QuestionResponseCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                questionResponseService.updateCredentials(requestCredentials));
    }

    @DeleteMapping("/{responseId}")
    public ResponseEntity<?> getQuestionResponseCredentials(
            @PathVariable String responseId) {
        questionResponseService.deleteCredentials(responseId);
        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
    }
}
