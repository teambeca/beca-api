package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.models.ProfileCredentials;
import com.r00t.becaapi.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/profile")
public class AdminProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/{page}")
    public ResponseEntity<?> getProfileCredentials(
            @PathVariable int page) {
        return ResponseEntity.ok().body(
                profileService.getCredentials(page));
    }

    @GetMapping("/by/id/{profileId}")
    public ResponseEntity<?> getProfileCredentialsByProfileId(
            @PathVariable String profileId) throws NotFoundException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsById(profileId));
    }

    @GetMapping("/by/user-id/{userId}")
    public ResponseEntity<?> getProfileCredentialsByUserId(
            @PathVariable String userId) throws NotFoundException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByUserId(userId));
    }

    @GetMapping("/by/email/{email}")
    public ResponseEntity<?> getProfileCredentialsByEmail(
            @PathVariable String email) throws NotFoundException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByEmail(email));
    }

    @GetMapping("/by/name/{name}")
    public ResponseEntity<?> getProfileCredentialsByName(
            @PathVariable String name) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByName(name));
    }

    @GetMapping("/by/job/{job}")
    public ResponseEntity<?> getProfileCredentialsByJob(
            @PathVariable String job) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByJob(job));
    }

    @GetMapping("/by/city-id/{cityId}")
    public ResponseEntity<?> getProfileCredentialsByCityId(
            @PathVariable String cityId) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByCityId(cityId));
    }

    @GetMapping("/by/gender/{gender}")
    public ResponseEntity<?> getProfileCredentialsByGender(
            @PathVariable int gender) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByGender(gender));
    }

    @GetMapping("/by/age/{age}")
    public ResponseEntity<?> getProfileCredentialsByAge(
            @PathVariable int age) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByAge(age));
    }

    @GetMapping("/by/age/greater-than/{age}")
    public ResponseEntity<?> getProfileCredentialsByAgeGreaterThan(
            @PathVariable int age) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByAgeGreaterThan(age));
    }

    @GetMapping("/by/age/less-than/{age}")
    public ResponseEntity<?> getProfileCredentialsByAgeLessThan(
            @PathVariable int age) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByAgeLessThan(age));
    }

    @GetMapping("/by/age/greater-than/{start}/and/less-than/{end}")
    public ResponseEntity<?> getProfileCredentialsByAgeBetween(
            @PathVariable int start,
            @PathVariable int end) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByAgeBetween(start, end));
    }

    @GetMapping("/by/creation-date/greater-than/{creationDate}")
    public ResponseEntity<?> getProfileCredentialsByCreationDateGreaterThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByCreationDateGreaterThan(creationDate));
    }

    @GetMapping("/by/creation-date/less-than/{creationDate}")
    public ResponseEntity<?> getProfileCredentialsByCreationDateLessThan(
            @PathVariable long creationDate) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByCreationDateLessThan(creationDate));
    }

    @GetMapping("/by/creation-date/greater-than/{start}/and/less-than/{end}")
    public ResponseEntity<?> getProfileCredentialsByCreationDateBetween(
            @PathVariable long start,
            @PathVariable long end) throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                profileService.getCredentialsByCreationDateBetween(start, end));
    }

    @PatchMapping
    public ResponseEntity<?> updateProfileCredentials(
            @RequestBody ProfileCredentials profileCredentials) throws NotFoundException {
        return ResponseEntity.ok().body(
                profileService.updateCredentials(profileCredentials)
        );
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<?> deleteProfileCredentials(
            @PathVariable String profileId) throws NotFoundException {
        profileService.deleteCredentials(profileId);
        return ResponseEntity.ok().body(
                Map.of("status", "ok"));
    }
}
