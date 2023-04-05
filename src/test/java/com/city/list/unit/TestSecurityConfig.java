package com.city.list.unit;

import com.city.list.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole(Role.ALLOW_EDIT.name(), Role.ALLOW_READ.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole(Role.ALLOW_EDIT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$12$uscJdet5Aqa5s0Y3nVVSQOxCYjMMukqpDeZw83/ycaBA0FgQO0gum")
                .roles(Role.ALLOW_READ.name())
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$12$P/862UOjnWDGhY0vFgXIsOlfIMKFHLmuagvYD6fv0L3JOAsgRiJ.W")
                .roles(Role.ALLOW_EDIT.name())
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(users());
        return authenticationProvider;

    }

}
