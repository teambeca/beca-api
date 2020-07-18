package com.r00t.becaapi.controllers.pub;

import com.r00t.becaapi.configs.SecurityPrincipal;
import com.r00t.becaapi.exceptions.AlreadyExistException;
import com.r00t.becaapi.exceptions.PermissionException;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.services.SecurityService;
import com.r00t.becaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private Random random;

    @PostMapping
    public ResponseEntity<?> signIn(
            @RequestBody UserLoginCredentials requestCredentials) throws PermissionException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestCredentials.getUsername(), requestCredentials.getPassword()));
        } catch (DisabledException e) {
            throw new PermissionException("AuthenticationController.Disabled.generateToken");
        } catch (BadCredentialsException e) {
            throw new PermissionException("AuthenticationController.BadCredentials.generateToken");
        }

        UserLoginCredentials userLoginCredentials = ((SecurityPrincipal) securityService.loadUserByUsername(
                requestCredentials.getUsername())).getUserLoginCredentials();
        return ResponseEntity.ok().body(
                securityService.createToken(userLoginCredentials));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(
            @RequestBody UserLoginCredentials requestCredentials) throws AlreadyExistException {
        return ResponseEntity.ok().body(
                securityService.createToken(
                        userService.insertCredentials(requestCredentials)));
    }

    @GetMapping("/sign-up/anonymous")
    public ResponseEntity<?> signUpAnonymous() throws AlreadyExistException {
        String username = "anonymous_";
        do {
            username += random.nextInt(100000 - 10000) + 10000;
        } while (userService.checkUsernameExist(username));

        UserLoginCredentials u = new UserLoginCredentials();
        u.setUsername(username);
        return ResponseEntity.ok().body(
                securityService.createToken(
                        userService.insertCredentials(u)));
    }
}
