package org.genose.helisius_spring_training.controller.routes;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.genose.helisius_spring_training.configuration.JWTService;
import org.genose.helisius_spring_training.entities.UserEntity;
import org.genose.helisius_spring_training.enums.UsersRolesEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(BaseRoutesController.USERS_URL)
public class UsersRoutesController extends BaseRoutesController {

	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final ServletRequest httpServletRequest;

	public UsersRoutesController(AuthenticationManager authenticationManager, JWTService jwtService, PasswordEncoder passwordEncoder, ServletRequest httpServletRequest) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.httpServletRequest = httpServletRequest;
	}


	@GetMapping(BaseRoutesController.LOGIN_GET_TEST_TOKEN_URL)
	public ResponseEntity<?> getFakeToken() {
		Map<String, String> fakeToken = jwtService.generateEncodedTokenForEmail("devel@genose.org");
		return ResponseEntity.ok(fakeToken);
	}

	@PostMapping(BaseRoutesController.LOGIN_URL)
	public ResponseEntity<?> getLogin(@Valid @RequestBody UserEntity argUser,
	                                  HttpServletResponse response) {
		try {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							argUser.getEmail(), argUser.getPassword()
					)
			);

			if (authentication.isAuthenticated()) {
				String token = jwtService.generateEncodedTokenForEmail(argUser.getEmail())
						.get(JWTService.COOKIE_TOKEN_NAME);

				ResponseCookie responseCookie = ResponseCookie
						.from(JWTService.COOKIE_TOKEN_NAME, token)
						.httpOnly(true)
						.secure(true)
						.domain(httpServletRequest.getServerName())
						.path("/")
						.maxAge(this.jwtService.getTokenExpiration())
						.build();

				response.addHeader("Set-Cookie", responseCookie.toString());

				return ResponseEntity.ok()
						.header("Authorization",
								JWTService.BEARER_TOKEN + " " + token
						).build();
			}
		} catch (BadCredentialsException badCredentialsException) {
			log.error("Bad credentials", badCredentialsException);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Utilisateur inconnu");
	}

	/* ****** ****** ****** ****** */
	@PostMapping(BaseRoutesController.LOGIN_REGISTER_URL)
	public ResponseEntity<?> register(@Valid @RequestBody UserEntity argUser) {
		try {
			argUser.setPassword(passwordEncoder.encode(argUser.getPassword()));
			argUser.setUserRole(UsersRolesEnum.USER);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Error", e);
		}
		return ResponseEntity.unprocessableEntity().build();
	}

	/* ****** ****** ****** ****** */
	@GetMapping("/details")
	public ResponseEntity<?> getDetails() {
		return ResponseEntity.ok("User details");
	}


}
