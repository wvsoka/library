package com.lista2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding.
 */
@Configuration
public class PasswordConfig {

    /**
     * Creates a password encoder bean using BCryptPasswordEncoder.
     *
     * @return The password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
