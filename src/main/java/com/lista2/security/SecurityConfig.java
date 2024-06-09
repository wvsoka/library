package com.lista2.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for security settings.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.token.key}")
    private String key;

    //w bazie danych role zapisuje sie ROLE_NAZWAROLI
    //w konfiguracji role zapisuje sie jako NAZWAROLI

    /**
     * Configures security filters and authorization rules.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(new JWTTokenFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers("/book/add").hasRole("LIBRARIAN")
                                        .requestMatchers("/book/delete/**").hasRole("LIBRARIAN")
                                        .requestMatchers("book/getAll").hasAnyRole("LIBRARIAN", "READER")
                                       // .requestMatchers("/book/getAll").permitAll()
                                        .requestMatchers("/book/getBook/**").hasAnyRole("LIBRARIAN", "READER")
                                        .requestMatchers("/loan/add").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/delete/**").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/getAll").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/getMyLoans").hasRole("READER")
                                        .requestMatchers("/loan/returnDate/**").hasRole("LIBRARIAN")
                                        .requestMatchers("/user/**").hasRole("LIBRARIAN")
                                        .requestMatchers("/opinion/add").hasRole("READER")
                                        .requestMatchers("/opinion/getAll").hasAnyRole("LIBRARIAN", "READER")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagment ->
                        sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
