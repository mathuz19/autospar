package com.example.demo.config;

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
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/",
                "/index.html",
                "/about.html",
                "/feature.html",
                "/service.html",
                "/contact",
                "/contact.html",
                "/api/contact",
                "/api/service",
                "/login.html",
                "/signup.html",
                "/forgot-password.html",
                "/addSlot.html", // Explicitly permit GET and POST
                "/parking/manageSlots", // Explicitly permit GET
                "/parking/allocateSlot", // Explicitly permit POST
                "/parking/deallocateSlot", // Explicitly permit POST
                "/parking/deleteSlot", // Explicitly permit POST
                "/parking/availableSlots", // Explicitly permit GET
                "/api/auth/**",
                "/css/**",
                "/js/**",
                "/images/**"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .csrf(csrf -> csrf.disable());
    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}