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
    public static final String EVENTS_URL = "/events";
    public static final String GROUPS_URL = "/groups";
    public static final String USERS_URL = "/users";
    public static final String AUTH_URL = "/auth";
    /* ****** ****** ****** ****** */
    public static final String LOGIN_URL = AUTH_URL + "/login";
    public static final String LOGOUT_URL = AUTH_URL + "/logout";
    public static final String LOGIN_REGISTER_URL = AUTH_URL + "/register";
    public static final String LOGIN_RESET_PASSWORD_URL = AUTH_URL + "/reset-password";

    public static final String LOGIN_GET_TEST_TOKEN_URL = USERS_URL + "/token";
    /* ****** ****** ****** ****** */
}