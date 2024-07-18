package org.genose.helisius_spring_training.controller;

import org.genose.helisius_spring_training.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfirmationTokenController {

    private final AuthenticationService authService;

    public ConfirmationTokenController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
        authService.confirmUser(token);
        return ResponseEntity.ok("User confirmed successfully");
    }
}
