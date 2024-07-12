package org.genose.helisius_spring_training.controller.routes;


import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@EqualsAndHashCode
@MappedSuperclass
@RequestMapping("")
@RestController
abstract public class BaseRoutesController {
    /* ****** ****** ****** ****** */
    /* ****** ****** ****** ****** */
    /* ****** ******
     Spring possede 2 Approche pour le filtrage URL
     ****** ****** */
    /* ****** ******
     L autre approche est l utilisation de
        @Preauthorize + ENUM
        @PreAuthorize("hasRole('ROLE_USER')")
    * ****** ****** */
    final public static String EVENTS_URL = "/events";
    final public static String GROUPS_URL = "/groups";
    final public static String USERS_URL = "/users";
    /* ****** ****** ****** ****** */
    final public static String LOGIN_URL = USERS_URL + "/login";
    final public static String LOGIN_REGISTER_URL = USERS_URL + "/users/login";
    final public static String LOGOUT_URL = USERS_URL + "/logout";
    final public static String LOGIN_RESET_PASSWORD_URL = USERS_URL + "/reset-password";
    final public static String LOGIN_FAILURE_URL = USERS_URL + "/login-failure";
    final public static String LOGIN_PASSWORD_FAILURE_URL = USERS_URL + "/reset-password-failure";
    /* ****** ****** ****** ****** */
    protected Logger logger = LoggerFactory.getLogger(BaseRoutesController.class);

    /* ****** ****** ****** ****** */
    /* ****** ****** ****** ****** */
    @GetMapping("")
    public String get(@RequestParam Map<String, String> allParams) {
        logger.info(
                this.getClass().getSimpleName() + " :: "
                        + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " Query params {}", allParams.entrySet());
        return "Hello World ! Base::"
                + this.getClass().getSimpleName()
                + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this);
    }

    /* ****** ****** ****** ****** */
    @GetMapping("/all")
    public Collection<Object> getAll(@RequestParam Map<String, String> allParams) {
        logger.info(
                this.getClass().getSimpleName() + " :: "
                        + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " Query params {}", allParams.entrySet());
        String message = "Hello World ! Base::"
                + this.getClass().getSimpleName()
                + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this);
        ArrayList<Object> list = new ArrayList<>();
        list.add(message);

        return list.stream().toList();
    }

    /* ****** ****** ****** ****** */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetailsById(@PathVariable int id,
                                                 @RequestParam Map<String, String> allParams) {
        logger.info(
                this.getClass().getSimpleName() + " :: "
                        + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " Query params {}", allParams.entrySet());

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("Hello World ! Base::"
                        + this.getClass().getSimpleName()
                        + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this));
    }

    /* ****** ****** ****** ****** */
    @PatchMapping("")
    @PutMapping("")
    @PostMapping("")
    public ResponseEntity<Object> saveEntity(@RequestBody Object entity,
                                             @RequestParam Map<String, String> allParams) {

        logger.info(
                this.getClass().getSimpleName() + " :: "
                        + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " Query params {}", allParams.entrySet());

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("From Class : Base::"
                        + this.getClass().getSimpleName()
                        + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this));
    }

    /* ****** ****** ****** ****** */
    @PatchMapping("/{id}")
    @PutMapping("/{id}")
    @PostMapping("/{id}")
    public ResponseEntity<Object> updateEntityByID(@RequestBody Object entity, @PathVariable int id) {
        logger.info(
                this.getClass().getSimpleName() + " :: "
                        + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " Query params {}", id);
        return ResponseEntity.unprocessableEntity().body("From Class : Base::" + this.getClass().getSimpleName() + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this));
    }

    /* ****** ****** ****** ****** */
    @DeleteMapping("")
    public ResponseEntity<Object> deleteEntity(@RequestParam Map<String, String> allParams) {
        logger.info(
                this.getClass().getSimpleName() + " :: "
                        + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " Query params {}", allParams.entrySet());

        return ResponseEntity.unprocessableEntity().body("From Class : Base::" + this.getClass().getSimpleName() + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this));
    }

    /* ****** ****** ****** ****** */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEntityByID(@PathVariable int id, @RequestParam Map<String, String> allParams) {
        logger.info(
                this.getClass().getSimpleName() + " :: "
                        + GNSClassStackUtils.getEnclosingMethodObject(this)
                        + " Query params {}", allParams.entrySet());
        return ResponseEntity.unprocessableEntity().body("From Class : Base::" + this.getClass().getSimpleName() + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this));
    }

    /* ****** ****** ****** ****** */
    @GetMapping("/hello")
    // .... @ApiOperation(value = "Hello World", notes = "Returns a greeting message")
    public String hello() {
        return "Hello, World!";
    }

    /* ****** ****** ****** ****** */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        String message = e.getMessage();
        String className = this.getClass().getSimpleName();
        String methodName = GNSClassStackUtils.getEnclosingMethodObject(this);
        String loggerMessageFormatted = String.format("Exception occurred in [%s] %s : %s ", className, methodName, message);
        // Send error to local Logger instance
        logger.error(loggerMessageFormatted, e);

        if (e instanceof IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loggerMessageFormatted);
        } else if (e instanceof NullPointerException) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(loggerMessageFormatted);
        } else if (e instanceof IllegalStateException) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(loggerMessageFormatted);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loggerMessageFormatted);
        }
    }

    /* ****** ****** ****** ****** */

}
