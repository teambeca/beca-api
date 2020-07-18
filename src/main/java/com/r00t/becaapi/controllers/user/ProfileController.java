package com.r00t.becaapi.controllers.user;

import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.ProfileCredentials;
import com.r00t.becaapi.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<?> getProfileCredentials(Authentication authentication) throws NotFoundException {
        String userId = (String) authentication.getCredentials();
        return ResponseEntity.ok().body(
                profileService.getCredentialsByUserId(userId));
    }

    @PatchMapping
    public ResponseEntity<?> updateProfileCredentials(
            Authentication authentication,
            @RequestBody ProfileCredentials requestCredentials) throws NotFoundException {
        String userId = (String) authentication.getCredentials();
        requestCredentials.setUserId(userId);
        return ResponseEntity.ok().body(
                profileService.updateCredentials(requestCredentials));
    }
}
