package com.example.demo72.config;

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
                .authorizeHttpRequests()
                .requestMatchers(
                        "/style/**", "/resources/**", "/", "/login", "/signup", "/trainings"
                )
                .permitAll()
                .requestMatchers("/AdminDirectory").hasRole("ADMIN")
                .requestMatchers("/change-role").hasRole("ADMIN")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/AdminDirectory")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();

        return http.build();
    }
}
