package fr.olprog_c.le_phare_culturel.controllers;

import java.util.Map;

import fr.olprog_c.le_phare_culturel.controllers.routes.RouteDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import fr.olprog_c.le_phare_culturel.configuration.JWTService;
import fr.olprog_c.le_phare_culturel.controllers.routes.RouteDefinition;
import fr.olprog_c.le_phare_culturel.dtos.AuthLoginPostDTO;
import fr.olprog_c.le_phare_culturel.dtos.AuthRegisterPostDTO;
import fr.olprog_c.le_phare_culturel.dtos.mapper.AuthDTOMapper;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import fr.olprog_c.le_phare_culturel.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class AuthController {

  private final AuthService authService;
  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthController(AuthService authService, JWTService jwtService, AuthenticationManager authenticationManager) {
    this.authService = authService;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping(RouteDefinition.Auth.LOGIN_URL)
  public ResponseEntity<?> login(@Valid @RequestBody AuthLoginPostDTO dto, HttpServletResponse response) {
    try {
      final Authentication authenticate = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
      if (authenticate.isAuthenticated()) {
        UserEntity user = (UserEntity) authenticate.getPrincipal();
        Map<String, String> tokens = jwtService.generateTokensForEmail(user.getEmail());

        ResponseCookie accessTokenCookie = ResponseCookie
            .from(JWTService.ACCESS_TOKEN_NAME, tokens.get(JWTService.ACCESS_TOKEN_NAME))
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(10 * 60)
            .build();

        ResponseCookie refreshTokenCookie = ResponseCookie
            .from(JWTService.REFRESH_TOKEN_NAME, tokens.get(JWTService.REFRESH_TOKEN_NAME))
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(30 * 24 * 60 * 60)
            .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        // Ajouter le token CSRF
        // CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        // ResponseCookie csrfCookie = ResponseCookie.from("XSRF-TOKEN",
        // csrfToken.getToken())
        // .httpOnly(false)
        // .secure(true)
        // .path("/")
        // .maxAge(30 * 60)
        // .build();

        // response.addHeader("Set-Cookie", csrfCookie.toString());

        return ResponseEntity.ok(AuthDTOMapper.responseDTO(user));
      }

    } catch (BadCredentialsException b) {
      throw new BadCredentialsException("Je ne vous connais pas!!");
    }
    return null;
  }

  @PostMapping(RouteDefinition.Auth.REGISTER_URL)
  public void register(@Valid @RequestBody AuthRegisterPostDTO dto) {
    if (dto.confirmPassword().equals(dto.password())) {
      this.authService.register(dto);
    } else {
      throw new HttpServerErrorException(HttpStatus.PRECONDITION_REQUIRED, "Les mots de passe ne sont pas identiques");
    }
  }

  @PostMapping("/auth/token/refresh")
  public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = null;
    for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
      if (JWTService.REFRESH_TOKEN_NAME.equals(cookie.getName())) {
        refreshToken = cookie.getValue();
      }
    }

    if (refreshToken != null && jwtService.isTokenExpired(refreshToken)) {
      String username = jwtService.extractUsername(refreshToken);
      Map<String, String> tokens = jwtService.generateTokensForEmail(username);

      ResponseCookie newAccessTokenCookie = ResponseCookie
          .from(JWTService.ACCESS_TOKEN_NAME, tokens.get(JWTService.ACCESS_TOKEN_NAME))
          .httpOnly(true)
          .secure(true)
          .path("/")
          .maxAge(10 * 60)
          .build();

      response.addHeader("Set-Cookie", newAccessTokenCookie.toString());

      return ResponseEntity.ok(Map.of("message", "Token refreshed"));
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Invalid refresh token"));
    }
  }
}
