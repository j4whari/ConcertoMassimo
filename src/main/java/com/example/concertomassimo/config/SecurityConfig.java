package com.example.concertomassimo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disabilita la protezione CSRF (opzionale)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())  // Permette tutte le richieste
                .formLogin(form -> form.disable())  // Disabilita il login predefinito
                .httpBasic(basic -> basic.disable());  // Disabilita l'autenticazione di base

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}