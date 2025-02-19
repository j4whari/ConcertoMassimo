package com.example.concertomassimo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disabilita la protezione CSRF (opzionale)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/auth", "/register", "/homepage", "/index").permitAll()  // Permette l'accesso a queste pagine senza autenticazione
                        .anyRequest().authenticated()  // Richiede autenticazione per tutte le altre richieste
                )
                .formLogin(form -> form
                        .loginPage("/auth")  // Specifica la pagina di login personalizzata
                        .loginProcessingUrl("controller/login")
                        .defaultSuccessUrl("/index")  // Reindirizza qui dopo un login riuscito
                        .failureUrl("/auth?error=true")  // Reindirizza qui in caso di errore di login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/auth?logout=true")  // Reindirizza qui dopo il logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}