package com.web.investment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/logout/**", "/api/auth/user/**", "/api/auth/update/**").permitAll()
                        .requestMatchers("/admin/products/create", "/admin/products/all").permitAll()
                        .requestMatchers( "/api/wallet/deposit/**" , "/api/wallet/withdraw/**", "/api/wallet/balance/**", "/api/wallet/transaction/history/**").permitAll()
                        .requestMatchers("/api/bank/add/**", "/api/bank/update/**", "/api/bank/get/**", "/api/bank/status/**").permitAll()
                        .requestMatchers("/api/purchases/buy", "/api/purchases/**").permitAll()
                        .requestMatchers("/api/profit/add", "/withdraws", "/withdraws/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable()); // Disable CSRF for testing via Postman
        return http.build();
    }
    
}
