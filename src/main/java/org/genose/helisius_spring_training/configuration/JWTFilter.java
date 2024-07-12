package org.genose.helisius_spring_training.configuration;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.Host;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import org.genose.helisius_spring_training.configuration.JWTService;

@Service
public class JWTFilter extends OncePerRequestFilter {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final org.genose.helisius_spring_training.configuration.JWTService JWTService;
    private UsersDetailsConfiguration userDetailsService;

    public JWTFilter(JWTService JWTService) {
        this.JWTService = JWTService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = null;
        String tokenFromCookie = null;
        String username = null;
        try {
            final Cookie[] cookies = request.getCookies();
            /* ****** ****** ***** ****** */
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getDomain().equals(response.getHeader("Host"))
                            && cookie.getName().equals(org.genose.helisius_spring_training.configuration.JWTService.COOKIE_TOKEN_NAME)
                    ) {
                        tokenFromCookie = cookie.getValue();

                        if (tokenFromCookie != null
                                && tokenFromCookie.startsWith(TOKEN_PREFIX)
                                && tokenFromCookie.length() > TOKEN_PREFIX.length()
                                && SecurityContextHolder.getContext().getAuthentication() == null
                        ) {
                            username = this.JWTService.extractUsername(tokenFromCookie);

                            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                        }
                        break;
                    }
                }
            }
            /* ****** ****** ***** ****** */

            try {
                if (tokenFromCookie == null) {
                    token = request.getHeader(HEADER_STRING);
                    if (token.startsWith(TOKEN_PREFIX)) {
                        String jwtToken = token.replace(TOKEN_PREFIX, "");

                        Claims claims = JWTService.getDecodedTokenClaims(token);
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
            }
        } catch (Exception e) {

            if (username != null
                    && !username.isEmpty()
            ) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // ... userDetails.getUsername();
                UsersEntity userEntity = (UsersEntity) userDetails;
                Map<String, String> tokenMap = JWTService.generateEncodedTokenFromUsersEntity(userEntity);
                String newToken = tokenMap.get("token");
                response.setHeader(HEADER_STRING, TOKEN_PREFIX + newToken);

            }
        }
    }
}
