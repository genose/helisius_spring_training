package org.genose.helisius_spring_training.controller.routes;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.genose.helisius_spring_training.configuration.JWTService;
import org.genose.helisius_spring_training.dto_mapper.UsersMapper;
import org.genose.helisius_spring_training.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "user", description = "the user API")
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

    private final JWTService jwtService;

    public UsersRoutesController( JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(
                jwtService.generateEncodedTokenForEmail("devel@genose.org")
        );
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam String bearer) {
        if (!jwtService.isTokenValid(bearer)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println(this.jwtService.isTokenExpired(bearer));
        System.out.println(this.jwtService.extractRoles(bearer));
        System.out.println(this.jwtService.extractUsername(bearer));
        return ResponseEntity.ok().build();
    }

}
