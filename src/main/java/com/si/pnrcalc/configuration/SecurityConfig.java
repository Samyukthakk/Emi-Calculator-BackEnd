package com.si.pnrcalc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**", "/api/pnr"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new MvcRequestMatcher.Builder(null)
                                .pattern("/api/pnr")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2/**")).permitAll()
                        .anyRequest().authenticated())
                .headers(header -> header.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                .cors(Customizer.withDefaults())
                .build();
    }
}
