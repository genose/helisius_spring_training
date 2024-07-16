package org.genose.helisius_spring_training.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.genose.helisius_spring_training.services.UsersService;
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
    private UsersService userDetailsService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = null;
        String tokenFromCookie = null;
        String username = null;
        Boolean isTokenExpired = true;
        try {
            final Cookie[] cookies = request.getCookies();
            /* ****** ****** ***** ****** */
            if (cookies != null) {
                /* ****** ****** ***** ****** */
                for (Cookie cookie : cookies) {
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
                    filterChain.doFilter(request, response);
                }
            } else {
                throw new ServletException("No token found");
            }
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

        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: error :: " + e.getMessage());

            if (username != null && !username.isEmpty()) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // ... userDetails.getUsername();
                UsersEntity userEntity = (UsersEntity) userDetails;
                Map<String, String> tokenMap = jwtService.generateEncodedTokenFromUsersEntity(userEntity);

                String newToken = tokenMap.get("token");
                response.setHeader(SecurityConfiguration.AUTHORIZATION_HEADER,
                        SecurityConfiguration.BEARER_TOKEN_PREFIX + newToken);

            }
        }
    }
}
