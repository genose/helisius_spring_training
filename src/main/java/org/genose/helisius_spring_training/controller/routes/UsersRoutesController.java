package org.genose.helisius_spring_training.controller.routes;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.genose.helisius_spring_training.configuration.JWTService;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.genose.helisius_spring_training.enums.UsersRolesEnum;
import org.genose.helisius_spring_training.repositories.UsersRepository;
import org.genose.helisius_spring_training.services.UsersService;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Map;

@RestController
@RequestMapping(BaseRoutesController.USERS_URL)
@Tag(name = BaseRoutesController.USERS_URL, description = "the user API")
public class UsersRoutesController extends BaseRoutesController {

    /* ** SwaggerUI / OpenApi
        @Operation(summary = "Creates list of users with given input array", tags = { "user" })
        @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
        @PostMapping(value = "/user/createWithArray", consumes = { "application/json" })
        default ResponseEntity<Void> createUsersWithArrayInput(
                @Parameter(description = "List of user object", required = true) @Valid @RequestBody List<User> user) {
            return ResponseEntity.ok().build();
        }
    ** */

    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ServletRequest httpServletRequest;
    private final UsersRepository usersRepository;

    public UsersRoutesController(UsersService usersService,
                                 AuthenticationManager authenticationManager,
                                 JWTService jwtService, PasswordEncoder passwordEncoder,
                                 ServletRequest httpServletRequest, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.httpServletRequest = httpServletRequest;
        this.usersRepository = usersRepository;
    }

    @GetMapping(BaseRoutesController.LOGIN_GET_TEST_TOKEN_URL)
    public ResponseEntity<?> getFakeToken() {
        Map<String, String> fakeToken = jwtService.generateEncodedTokenForEmail("devel@genose.org");
        this.logger.info(
                GNSClassStackUtils.getEnclosingClass()
                        + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " :: fakeToken ::" + fakeToken);
        return ResponseEntity.ok(fakeToken);
    }

    @PostMapping(BaseRoutesController.LOGIN_URL)
    public ResponseEntity<?> getLogin(@Valid @RequestBody UsersEntity argUser,
                                      HttpServletResponse response) {
        try {
            this.logger.info(
                    MessageFormat.format("{0} :: {1} :: {2}", GNSClassStackUtils.getEnclosingClass(), GNSClassStackUtils.getEnclosingMethodObject(this), argUser));
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            argUser.getEmail(), argUser.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                this.logger.info(
                        MessageFormat.format("{0} :: {1} :: Success :: {2}", GNSClassStackUtils.getEnclosingClass(), GNSClassStackUtils.getEnclosingMethodObject(this), argUser));

                String token = jwtService.generateEncodedTokenForEmail(argUser.getEmail())
                        .get(JWTService.COOKIE_TOKEN_NAME);

                ResponseCookie responseCookie = ResponseCookie
                        .from(JWTService.COOKIE_TOKEN_NAME, token)
                        .httpOnly(true)
                        .secure(true)
                        .domain(httpServletRequest.getServerName())
                        .path("/")
                        .maxAge(JWTService.getTokenExpiration())
                        .build();

                response.addHeader("Set-Cookie", responseCookie.toString());
                this.logger.info("{} :: {} :: {} :: {}", GNSClassStackUtils.getEnclosingClass(), GNSClassStackUtils.getEnclosingMethodObject(this), argUser, responseCookie);

                return ResponseEntity.ok()
                        .header("Authorization",
                                JWTService.BEARER_TOKEN + " " + token
                        ).build();
            }
        } catch (BadCredentialsException badCredentialsException) {
            logger.error("{} :: {} :: {}", GNSClassStackUtils.getEnclosingClass(), GNSClassStackUtils.getEnclosingMethodObject(this), badCredentialsException.getMessage());
            logger.error(badCredentialsException.getMessage(), badCredentialsException);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Utilisateur inconnu");
    }

    /* ****** ****** ****** ****** */
    @PostMapping(BaseRoutesController.LOGIN_REGISTER_URL)
    public ResponseEntity<?> register(@Valid @RequestBody UsersEntity argUser) {
        try {
            this.logger.info(
                    MessageFormat.format("{0} :: {1} :: {2}", GNSClassStackUtils.getEnclosingClass(), GNSClassStackUtils.getEnclosingMethodObject(this), argUser));
            argUser.setPassword(passwordEncoder.encode(argUser.getPassword()));
            argUser.setUserRole(UsersRolesEnum.USER);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            this.logger.error(
                    MessageFormat.format("{0} :: {1} :: {2}", GNSClassStackUtils.getEnclosingClass(), GNSClassStackUtils.getEnclosingMethodObject(this), e.getMessage()));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    /* ****** ****** ****** ****** */
    @GetMapping("/details")
    public ResponseEntity<?> getDetails() {
        return ResponseEntity.ok("User details");
    }


}
