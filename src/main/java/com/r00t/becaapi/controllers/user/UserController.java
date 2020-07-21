package com.r00t.becaapi.controllers.user;

import com.r00t.becaapi.exceptions.BaseException;
import com.r00t.becaapi.exceptions.NotFoundException;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserLoginCredentials(Authentication authentication) throws NotFoundException {
        String userId = (String) authentication.getCredentials();
        return ResponseEntity.ok().body(
                userService.getCredentialsById(userId));
    }

    @PatchMapping
    public ResponseEntity<?> updateUserLoginCredentials(
            Authentication authentication,
            @RequestBody UserLoginCredentials requestCredentials) throws BaseException {
        String userId = (String) authentication.getCredentials();
        requestCredentials.setId(userId);
        if (requestCredentials.getPassword() != null)
            if (!isPasswordValid(requestCredentials.getPassword()))
                throw new BaseException(422, "invalidParameter", "password", "password field can't be blank");
        return ResponseEntity.ok().body(
                userService.updateCredentialsByRequest(requestCredentials));
    }

    private boolean isPasswordValid(String password) {
        return !password.isEmpty() && password.length() > 5;
    }
}
