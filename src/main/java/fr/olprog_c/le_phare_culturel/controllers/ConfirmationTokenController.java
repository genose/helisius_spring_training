package fr.olprog_c.le_phare_culturel.controllers;

import fr.olprog_c.le_phare_culturel.controllers.routes.RouteDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.olprog_c.le_phare_culturel.services.AuthService;

@Controller
public class ConfirmationTokenController {

  private final AuthService authService;

  public ConfirmationTokenController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping(RouteDefinition.Auth.EMAIL_CONFIRMATION_URL)
  public String confirmUser(@RequestParam("token") String token) {
    authService.confirmUser(token);
    return "confirmToken";
  }
}