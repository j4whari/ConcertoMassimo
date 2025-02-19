package com.example.concertomassimo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF (opzionale)
                .authorizeHttpRequests(auth -> auth
                        // Permetti l'accesso alle risorse statiche
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Consenti l'accesso alle pagine di login, registrazione, homepage, index e ai nostri endpoint personalizzati
                        .requestMatchers("/auth", "/controller/register", "/controller/login", "/homepage", "/index", "/form", "/controller/ticket").permitAll()
                        .anyRequest().authenticated()
                )
                // Disabilitiamo il formLogin di Spring Security per usare il nostro controller personalizzato
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth?logout=true")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

