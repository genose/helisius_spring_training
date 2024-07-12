package org.genose.helisius_spring_training.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.genose.helisius_spring_training.controller.routes.BaseRoutesController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JWTFilter JWTFilters;

    /* ****** ****** ****** ****** */
    @Bean
    public JWTFilter jwtFilter(JWTService jwtService) {
        return new JWTFilter(jwtService);
    }

    /* ****** ****** ****** ****** */
    public SecurityConfiguration(@Qualifier("JWTFilter") JWTFilter jwtFilters) {
        JWTFilters = jwtFilters;
    }

    /* ****** ****** ****** ****** */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                        request -> {
                            request
                                    /* ****** ******
                                     Spring possede 2 Approche pour le filtrage URL
                                     ****** ****** */
                                    /* ****** ******
                                     L autre approche est l utilisation de
                                        @Preauthorize + ENUM
                                        @PreAuthorize("hasRole('ROLE_USER')")
                                    * ****** ****** */
                                    .requestMatchers(
                                            BaseRoutesController.LOGIN_URL,
                                            BaseRoutesController.LOGIN_REGISTER_URL,
                                            BaseRoutesController.LOGOUT_URL,
                                            BaseRoutesController.LOGIN_RESET_PASSWORD_URL,
                                            BaseRoutesController.LOGIN_FAILURE_URL,
                                            BaseRoutesController.LOGIN_PASSWORD_FAILURE_URL
                                    ).permitAll()
                                    /* ****** ****** ****** ****** */
                                    .requestMatchers(BaseRoutesController.GROUPS_URL + "/**").permitAll()
                                    /* ****** ****** ****** ****** */
                                    .requestMatchers(BaseRoutesController.EVENTS_URL + "/**").permitAll()
                                    /* ****** ****** ****** ****** */
                                    .requestMatchers("/").permitAll()
                                    /* ****** ****** ****** ****** */
                                    .anyRequest()
                            //        .authenticated()
                            ;
                            /* ****** ****** ****** ****** */
                        }
                ).addFilterBefore(
                        JWTFilters, UsernamePasswordAuthenticationFilter.class
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            Map<String, ?> errors = Map.of("status", HttpServletResponse.SC_UNAUTHORIZED,
                                    "error_message", "Non autorisé");
                            response.getWriter().write(new ObjectMapper().writeValueAsString(errors));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            Map<String, ?> errors = Map.of("status", HttpServletResponse.SC_FORBIDDEN,
                                    "error_message", "Accès interdit");
                            response.getWriter().write(new ObjectMapper().writeValueAsString(errors));
                        }))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("*"));
            cors.setAllowedMethods(List.of("*"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
