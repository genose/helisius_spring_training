package fr.olprog_c.le_phare_culturel.controllers;

import fr.olprog_c.le_phare_culturel.controllers.routes.RouteDefinition;
import fr.olprog_c.le_phare_culturel.dtos.user.UserNewPasswordPutRequestDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserAvatarPutRequestDTO;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import fr.olprog_c.le_phare_culturel.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    /* ******* ****** ****** ****** */
    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService An object of UserService class.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method is used to get the user object using UserEntity.
     *
     * @param user An object of UserEntity class.
     * @return ResponseEntity object which contains User Response Data Transfer Object
     * or error if not processed.
     */
    @GetMapping(RouteDefinition.Users.PROFILE_URL)
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserEntity user) {
        System.out.println("Received Get Request :" + user);
        if (userService.convertRequestDtoToEntity(body, user).save()) {
            return ResponseEntity.ok(userService.convertEntityToResponseDTO(user));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    /**
     * This method is used to set new password.
     *
     * @param body An object of UserNewPasswordPutRequestDTO class which contains new password.
     * @param user An object of UserEntity class which is currently authenticated.
     * @return ResponseEntity object which contains User Response Data Transfer Object
     * or error if not processed.
     */
    @PutMapping(RouteDefinition.Users.CHANGE_PASSWORD_URL)
    public ResponseEntity<?> putNewPassword(
            @Valid @RequestBody UserNewPasswordPutRequestDTO body,
            @AuthenticationPrincipal UserEntity user
    ) {
        System.out.println("Received Put Request :" + user);
        if (userService.convertRequestNewPasswordDtoToEntity(body, user).save()) {
            return ResponseEntity.ok(userService.convertEntityToResponseDTO(user));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    /**
     * This method is used to set new avatar.
     *
     * @param body An object of UserAvatarPutRequestDTO class which contains new avatar url.
     * @param user An object of UserEntity class which is currently authenticated.
     * @return ResponseEntity object which contains User Response Data Transfer Object
     * or error if not processed.
     */
    /* ******* ****** ****** ****** */
    @PutMapping(RouteDefinition.Users.AVATAR_URL)
    public ResponseEntity<?> putNewAvatar(
            @Valid @RequestBody UserAvatarPutRequestDTO body,
            @AuthenticationPrincipal UserEntity user
    ) {
        System.out.println("Received Put Request :" + user);
        if (userService.convertRequestAvatarDtoToEntity(body, user).save()) {
            return ResponseEntity.ok(userService.convertEntityToResponseDTO(user));
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
