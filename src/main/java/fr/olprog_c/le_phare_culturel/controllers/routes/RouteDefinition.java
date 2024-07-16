package fr.olprog_c.le_phare_culturel.controllers.routes;

public class RouteDefinition {
  final public static String EVENTS_URL = "/events";

  final public static String GROUPS_URL = "/groups";

  final public static String USERS_URL = "/users";

  final public static String AUTH_URL = "/auth";

  // auth
  final public static String LOGIN_URL = AUTH_URL + "/sign-in";
  final public static String LOGIN_REGISTER_URL = AUTH_URL + "/sign-up";

  // users
  final public static String LOGOUT_URL = USERS_URL + "/logout";
  final public static String LOGIN_RESET_PASSWORD_URL = USERS_URL + "/reset-password";
}
