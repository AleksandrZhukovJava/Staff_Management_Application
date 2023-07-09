package ru.skypro.lessons.springboot.webLibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin.loginProcessingUrl("/login"))
                .logout(logout -> logout.logoutUrl("/logout"))
                .authorizeHttpRequests(this::customizeRequest).build();
    }

    private void customizeRequest(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        try {
            registry.requestMatchers(HttpMethod.GET).hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST).hasRole("ADMIN");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}