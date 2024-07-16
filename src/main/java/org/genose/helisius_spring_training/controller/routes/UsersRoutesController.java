package org.genose.helisius_spring_training.controller.routes;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.genose.helisius_spring_training.configuration.JWTService;
import org.genose.helisius_spring_training.configuration.SecurityConfiguration;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Map;

@RestController
@Tag(name = RouteDefinitions.USERS_URL, description = "the user API")
public class UsersRoutesController {

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
    private final UsersRepository usersRepository;

    public UsersRoutesController(UsersService usersService,
                                 AuthenticationManager authenticationManager,
                                 JWTService jwtService, PasswordEncoder passwordEncoder,
                                 UsersRepository usersRepository) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @GetMapping(RouteDefinitions.LOGIN_GET_TEST_TOKEN_URL)
    public ResponseEntity<?> getFakeToken() {
        System.out.println(" FakeToken Called .... ");
        GNSClassStackUtils.logger.info(
                GNSClassStackUtils.getEnclosingClass()
                        + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " :: fakeToken :: CALLED ");
        Map<String, String> fakeToken = jwtService.generateEncodedTokenForEmail("devel@genose.org");
        GNSClassStackUtils.logger.info(
                GNSClassStackUtils.getEnclosingClass()
                        + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " :: fakeToken ::" + fakeToken);
        return ResponseEntity.ok(fakeToken);
    }

    @PostMapping(RouteDefinitions.LOGIN_URL)
    public ResponseEntity<?> getLogin(@Valid @RequestBody UsersEntity argUser,
                                      HttpServletResponse response) {
        try {
            GNSClassStackUtils.logger.info(
                    MessageFormat.format("{0} :: {1} :: {2}",
                            GNSClassStackUtils.getEnclosingClass(),
                            GNSClassStackUtils.getEnclosingMethodObject(this),
                            argUser));

            /* ****** ****** ***** ****** */
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            argUser.getEmail(), argUser.getPassword()
                    )
            );

            /* ****** ****** ***** ****** */
            if (authentication.isAuthenticated()) {

                String encodedToken = jwtService.generateEncodedTokenForEmail(argUser.getEmail())
                        .get(SecurityConfiguration.COOKIE_TOKEN_NAME);

                /* ****** ****** ***** ****** */
                GNSClassStackUtils.logger.info("{} :: {} :: Success :: {} :: TOKEN :: {}",
                        GNSClassStackUtils.getEnclosingClass(),
                        GNSClassStackUtils.getEnclosingMethodObject(this),
                        argUser,
                        encodedToken
                );

                /* ****** ****** ***** ****** */
                ResponseCookie responseCookie = ResponseCookie
                        .from(SecurityConfiguration.COOKIE_TOKEN_NAME, encodedToken)
                        .httpOnly(true)
                        .secure(true)
                        // .domain(httpServletRequest.getServerName())
                        .path("/")
                        .maxAge(JWTService.getTokenExpiration())
                        .build();

                /* ****** ****** ***** ****** */
                response.addHeader(SecurityConfiguration.SET_COOKIE_HEADER, responseCookie.toString());

                GNSClassStackUtils.logger.info("{} :: {} :: {} :: {}",
                        GNSClassStackUtils.getEnclosingClass(),
                        GNSClassStackUtils.getEnclosingMethodObject(this),
                        argUser,
                        responseCookie);

                /* ****** ****** ***** ****** */
                return ResponseEntity.ok()
                        .header(SecurityConfiguration.AUTHORIZATION_HEADER,
                                SecurityConfiguration.BEARER_TOKEN_PREFIX + " " + encodedToken
                        ).build();

                /* ****** ****** ***** ****** */
            }
        } catch (BadCredentialsException badCredentialsException) {
            GNSClassStackUtils.logger.error("{} :: {} :: {}", GNSClassStackUtils.getEnclosingClass(), GNSClassStackUtils.getEnclosingMethodObject(this), badCredentialsException.getMessage());
            GNSClassStackUtils.logger.error(badCredentialsException.getMessage(), badCredentialsException);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Utilisateur inconnu");
    }

    /* ****** ****** ****** ****** */
    @PostMapping(RouteDefinitions.LOGIN_REGISTER_URL)
    public ResponseEntity<?> register(@Valid @RequestBody UsersEntity argUser) {
        try {
            GNSClassStackUtils.logger.info("{0} :: {1} :: {2}",
                    GNSClassStackUtils.getEnclosingClass(),
                    GNSClassStackUtils.getEnclosingMethodObject(this),
                    argUser);

            argUser.setPassword(passwordEncoder.encode(argUser.getPassword()));
            argUser.setUserRole(UsersRolesEnum.USER);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            GNSClassStackUtils.logger.error(
                    "{} :: {} :: {}",
                    GNSClassStackUtils.getEnclosingClass(),
                    GNSClassStackUtils.getEnclosingMethodObject(this),
                    e.getMessage());
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    /* ****** ****** ****** ****** */
    @GetMapping("/details")
    public ResponseEntity<?> getDetails() {
        return ResponseEntity.ok("User details");
    }


}
