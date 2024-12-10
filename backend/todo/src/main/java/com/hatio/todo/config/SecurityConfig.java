package com.hatio.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())
                // Configure authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/projects/**", "/api/projects/{projectId}/todos/**").permitAll() // Replace `antMatchers` with `requestMatchers`
                        .anyRequest().authenticated()
                )

                // Configure HTTP Basic authentication
                .httpBasic(withDefaults());

        return http.build();
    }
}
