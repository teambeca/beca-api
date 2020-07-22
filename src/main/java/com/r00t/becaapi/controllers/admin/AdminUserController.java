package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.converters.PageConverter;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PageConverter pageConverter;

    @GetMapping("/{page}")
    public ResponseEntity<?> getUserLoginCredentials(
            @PathVariable int page) {
        return ResponseEntity.ok().body(
                pageConverter.convertPageToMap(
                        userService.getCredentials(page)));
    }

    @GetMapping("/by/id/{userId}")
    public ResponseEntity<?> getUserLoginCredentialsByUserId(
            @PathVariable String userId) throws NotFoundException {
        return ResponseEntity.ok().body(
                userService.getCredentialsById(userId));
    }

    @GetMapping("/by/username/{username}")
    public ResponseEntity<?> getUserLoginCredentialsByUsername(
            @PathVariable String username) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByUsername(username));
    }

    @GetMapping("/by/role/{role}")
    public ResponseEntity<?> getUserLoginCredentialsByRole(
            @PathVariable String role) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByRole(role));
    }

    @GetMapping("/by/score/greater-than/{score}")
    public ResponseEntity<?> getUserLoginCredentialsByScoreGreaterThan(
            @PathVariable int score) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByScoreGreaterThan(score));
    }

    @GetMapping("/by/score/less-than/{score}")
    public ResponseEntity<?> getUserLoginCredentialsByScoreLessThan(
            @PathVariable int score) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByScoreLessThan(score));
    }

    @GetMapping("/by/score/greater-than/{start}/and/less-than/{end}")
    public ResponseEntity<?> getUserLoginCredentialsByScoreBetween(
            @PathVariable int start,
            @PathVariable int end) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByScoreBetween(start, end));
    }

    @GetMapping("/by/creation-date/greater-than/{creationDate}")
    public ResponseEntity<?> getUserLoginCredentialsByCreationDateGreaterThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByCreationDateGreaterThan(creationDate));
    }

    @GetMapping("/by/creation-date/less-than/{creationDate}")
    public ResponseEntity<?> getUserLoginCredentialsByCreationDateLessThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByCreationDateLessThan(creationDate));
    }

    @GetMapping("/by/creation-date/greater-than/{start}/and/less-than/{end}")
    public ResponseEntity<?> getUserLoginCredentialsByCreationDateBetween(
            @PathVariable long start,
            @PathVariable long end) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getCredentialsByCreationDateBetween(start, end));
    }

    @PatchMapping
    public ResponseEntity<?> updateUserLoginCredentials(
            @RequestBody UserLoginCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                userService.updateCredentials(requestCredentials));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserLoginCredentials(
            @PathVariable String userId) {
        userService.deleteCredentials(userId);
        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
    }
}
