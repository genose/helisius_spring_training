package org.genose.helisius_spring_training.controller.routes;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.genose.helisius_spring_training.configuration.JWTService;
import org.genose.helisius_spring_training.dtos.UsersPostRequestDTO;
import org.genose.helisius_spring_training.services.UsersService;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    private final ServletRequest httpServletRequest;

    public UsersRoutesController(UsersService usersService,
                                 AuthenticationManager authenticationManager,
                                 JWTService jwtService,
                                 ServletRequest httpServletRequest) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("/token")
    @PreAuthorize("hasAnyRole('ROLE_USER,ROLE_ADMIN')")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(
                jwtService.generateEncodedTokenForEmail("devel@genose.org")
        );
    }

    @PostMapping(BaseRoutesController.LOGIN_URL)
    public ResponseEntity<?> getLogin(@Valid @RequestBody UsersPostRequestDTO argUser,
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
                        .maxAge(JWTService.getTokenExpiration())
                        .build();

                response.addHeader("Set-Cookie", responseCookie.toString());

                return ResponseEntity.ok()
                        .header("Authorization",
                                JWTService.BEARER_TOKEN + " " + token
                        ).build();
            }
        } catch (BadCredentialsException badCredentialsException) {
            logger.error(badCredentialsException.getMessage(), badCredentialsException);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Utilisateur inconnu");
    }

    /* ****** ****** ****** ****** */
    @PostMapping(BaseRoutesController.LOGIN_REGISTER_URL)
    public ResponseEntity<?> register(@Valid @RequestBody UsersPostRequestDTO argUser) {
        try {
            this.logger.info(GNSClassStackUtils.getEnclosingMethodObject(this) + " :: " + argUser);
            usersService.save(argUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    /* ****** ****** ****** ****** */
    @GetMapping("/details")
    public ResponseEntity<?> getDetails() {
        return ResponseEntity.ok("User details");
    }


}
