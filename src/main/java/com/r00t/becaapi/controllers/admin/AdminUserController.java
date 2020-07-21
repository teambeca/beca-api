package com.r00t.becaapi.controllers.admin;

import com.r00t.becaapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @GetMapping("/by/id/{userId}")
    @GetMapping("/by/username/{username}")
    @GetMapping("/by/role/{role}")

    @GetMapping("/by/score/greater-than/{score}")
    @GetMapping("/by/score/less-than/{score}")
    @GetMapping("/by/score/greater-than/{start}/and/less-than/{end}")

    @GetMapping("/by/creation-date/greater-than/{creationDate}")
    @GetMapping("/by/creation-date/less-than/{creationDate}")
    @GetMapping("/by/creation-date/greater-than/{start}/and/less-than/{end}")

    @PatchMapping

    @DeleteMapping("/{userId}")
}
