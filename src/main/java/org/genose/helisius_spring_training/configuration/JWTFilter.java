package org.genose.helisius_spring_training.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.genose.helisius_spring_training.services.UserService;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Service
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final ObjectMapper objectMapper;
    private UserService userDetailsService;

    public JWTFilter(JWTService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = null;
        String tokenFromCookie = null;
        String username = null;
        boolean isTokenExpired = true;
        try {
            logger.info(" ..... doFilterInternal .... ");
            final Cookie[] cookies = request.getCookies();
            /* ****** ****** ***** ****** */
            if (cookies != null) {
                /* ****** ****** ***** ****** */
                for (Cookie cookie : cookies) {
                    logger.info("cookie : " + cookie.getName());

                    if (cookie.getName().equals(SecurityConfiguration.COOKIE_TOKEN_NAME)
                        // && cookie.getDomain().equals(response.getHeader("Host"))
                    ) {
                        tokenFromCookie = cookie.getValue();
                        logger.info("Found token from cookie: " + tokenFromCookie);
                        break;
                    }
                }

                /* ****** ****** ***** ****** */
                if (tokenFromCookie != null) {

                    isTokenExpired = this.jwtService.isTokenExpired(tokenFromCookie);
                    username = this.jwtService.extractUsername(tokenFromCookie);
                    logger.info("Found username: " + username);
                    logger.info("Token is expired: " + isTokenExpired);
                    if (!isTokenExpired
                            && username != null
                            && (SecurityContextHolder.getContext().getAuthentication() == null)) {

                        UserDetails user = this.userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        user, null,
                                        user.getAuthorities()
                                );
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }

                }
            } else {
                logger.info("No Cookies Found for username");
                // throw new ServletException("No token found");
            }
            filterChain.doFilter(request, response);
            /* ****** ****** ***** ****** */

           /* autre methode ....
            try {
                if (tokenFromCookie == null) {
                    token = request.getHeader(SecurityConfiguration.HEADER_STRING);
                    if (token.startsWith(SecurityConfiguration.BEARER_TOKEN_PREFIX)) {
                        String jwtToken = token.replace(SecurityConfiguration.BEARER_TOKEN_PREFIX, "");

                        Claims claims = jwtService.getDecodedTokenClaims(token);
                        username = claims.getSubject();
                        if (username != null) {
                            Logger.getLogger(this.getClass().getSimpleName()).info(this.getClass() + " :: Tokenized User : " + username);
                        } else {
                            Logger.getLogger(this.getClass().getSimpleName()).info(this.getClass() + " :: NO Tokenized User : " + username);
                        }

                    }
                }
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getSimpleName()).info(this.getClass() + " :: Tokenized Error : " + e.getMessage());
            } */

        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            Map<String, ?> errors = Map.of("status", HttpServletResponse.SC_UNAUTHORIZED, "error_message", "JWT expiré");
            response.getWriter().write(objectMapper.writeValueAsString(errors));
            logger.error(this.getClass().getSimpleName() + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: error :: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            Map<String, ?> errors = Map.of("status", HttpServletResponse.SC_BAD_REQUEST, "error_message", "JWT invalide");
            response.getWriter().write(objectMapper.writeValueAsString(errors));
            logger.error(this.getClass().getSimpleName() + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: error :: " + e.getMessage());
        }

            /* if (username != null && !username.isEmpty()) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // ... userDetails.getUsername();
                UsersEntity userEntity = (UsersEntity) userDetails;
                Map<String, String> tokenMap = jwtService.generateEncodedTokenFromUsersEntity(userEntity);

                String newToken = tokenMap.get("token");
                response.setHeader(SecurityConfiguration.AUTHORIZATION_HEADER,
                        SecurityConfiguration.BEARER_TOKEN_PREFIX + newToken);

            } */

    }
}
