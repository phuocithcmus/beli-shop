package org.beli.config;

import org.beli.config.auth.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/*").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/*").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/*/*").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/*").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/v1/*").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/v1/*/*").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/api/v1/*").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/*").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/*/*").permitAll()
//                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
