package org.genose.helisius_spring_training.controller.routes;

public class RouteDefinitions {
    /* ****** ****** ****** ****** */
    /* ****** ****** ****** ****** */
    /* ****** ******
     SpringBoot possede Deux Approche pour le filtrage URL
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
    final public static String LOGIN_GET_TEST_TOKEN_URL = USERS_URL + "/token";
    /* ****** ****** ****** ****** */
}