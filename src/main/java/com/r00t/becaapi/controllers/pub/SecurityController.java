package com.r00t.becaapi.controllers.pub;

import com.r00t.becaapi.configs.SecurityPrincipal;
import com.r00t.becaapi.models.UserLoginCredentials;
import com.r00t.becaapi.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityService securityService;

    @PostMapping
    public ResponseEntity<?> createToken(
            @Valid @RequestBody UserLoginCredentials requestCredentials) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestCredentials.getUsername(), requestCredentials.getPassword()));
        } catch (DisabledException e) {
            //throw new SPermissionException("AuthenticationController.Disabled.generateToken");
        } catch (BadCredentialsException e) {
            //throw new SPermissionException("AuthenticationController.BadCredentials.generateToken");
        }

        UserLoginCredentials userLoginCredentials = ((SecurityPrincipal) securityService.loadUserByUsername(
                requestCredentials.getUsername())).getUserLoginCredentials();
        return ResponseEntity.ok().body(
                securityService.createToken(userLoginCredentials));
    }
}
