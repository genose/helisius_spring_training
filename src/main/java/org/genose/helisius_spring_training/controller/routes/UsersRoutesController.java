package org.genose.helisius_spring_training.controller.routes;

import io.swagger.v3.oas.annotations.tags.Tag;
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
}
