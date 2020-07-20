package com.r00t.becaapi.controllers.pri;

import com.r00t.becaapi.exceptions.ServiceUnavailableException;
import com.r00t.becaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getLeaderboard() throws ServiceUnavailableException {
        return ResponseEntity.ok().body(
                userService.getTopCredentials());
    }
}
