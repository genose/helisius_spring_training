package org.genose.helisius_spring_training.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {

    private final UserDetailsService userDetailsService;

    @Value("${jwt-secret:keysecret}")
    private String secret;

    @Value("${jwt-expiration:1440}")
    private Long tokenExpiration;

    @Getter
    private Map<String, String> encodedTokenWithBearer;

    private SecretKey secretSigningKeyForJWT;
    private Map<String, Object> encodeClaims = null;

    /* ****** ****** ****** ****** */
    /* ****** ****** ****** ****** */
    public JWTService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /* ****** ****** ****** ****** */
    @PostConstruct
    private void init() {
        this.secretSigningKeyForJWT = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
        this.encodeClaims = null;
    }

    /* ****** ****** ****** ****** */
    public Map<String, String> generateEncodedTokenForEmail(String email) {
        return generateEncodedToken(userDetailsService.loadUserByUsername(email));
    }

    /* ****** ****** ****** ****** */
    public Map<String, String> generateEncodedTokenForUsername(String username) {
        return generateEncodedToken(userDetailsService.loadUserByUsername(username));
    }

    /* ****** ****** ****** ****** */
    public Map<String, String> generateEncodedTokenFromUsersEntity(UsersEntity argUser) {
        generateEncodedToken(userDetailsService.loadUserByUsername(argUser.getEmail()));
        argUser.setEncodedToken(this.encodedTokenWithBearer.toString());
        return this.encodedTokenWithBearer;
    }

    /* ****** ****** ****** ****** */
    public Map<String, String> generateEncodedToken(UserDetails argUserDetails) {
        final Long jwtDateNow = System.currentTimeMillis();
        final Date jwtExpirationDate = new Date(jwtDateNow + tokenExpiration);

        this.encodeClaims = Map.of(
                "email", argUserDetails.getUsername(),
                "roles", argUserDetails.getAuthorities(),
                Claims.EXPIRATION, jwtExpirationDate,
                Claims.ISSUED_AT, jwtDateNow,
                Claims.SUBJECT, argUserDetails.getUsername()

        );
        // claims.put("roles", argUserDetails.getAuthorities());
        final String localEncodedToken = Jwts.builder()
                .claims(encodeClaims)
                // .setSubject(argUserDetails.getUsername())
                // .setIssuedAt(jwtDateNow)
                // .setExpiration(jwtExpirationDate)
                .signWith(secretSigningKeyForJWT)
                .compact();
        this.encodedTokenWithBearer = Map.of("Bearer", localEncodedToken
                , "ExpiresAt", jwtExpirationDate.toString()
        );
        return this.encodedTokenWithBearer;
    }

    /* ****** ****** ****** ****** */
    private Claims getDecodedTokenClaims(String argToken) {
        return (Claims) Jwts.parser()
                .verifyWith(secretSigningKeyForJWT)
                .build()
                .parse(argToken)
                .getPayload();
    }

    /* ****** ****** ****** ****** */
    public boolean isTokenValid(String argToken) {
        try {
            getDecodedTokenClaims(argToken);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /* ****** ****** ****** ****** */
    public boolean isTokenExpired(String argToken) {
        try {
            return (new Date()).after(getDecodedTokenClaims(argToken).getExpiration());
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractRoles(String token) {
        try {
            return (String) getDecodedTokenClaims(token).get("role");
        } catch (JwtException e) {
            return null;
        }
    }

    /* ****** ****** ****** ****** */
    public String extractUsername(String argToken) {
        try {
            return getDecodedTokenClaims(argToken).getSubject();
        } catch (JwtException e) {
            return null;
        }

    }

    /* ****** ****** ****** ****** */
    private String extractPayload(String argToken) {
        try {
            return getDecodedTokenClaims(argToken).getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

}
