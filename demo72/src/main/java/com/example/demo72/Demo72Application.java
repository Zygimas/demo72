package com.example.demo72;

import com.example.demo72.user.CustomUserDetailsService;
import com.example.demo72.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.Set;

@SpringBootApplication
public class Demo72Application implements CommandLineRunner {

    private final UserRepository userRepository;

    public Demo72Application(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Demo72Application.class, args);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new CustomUserDetailsService(userRepository));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails god = User
                .withUsername("god")
                .password(passwordEncoder().encode("god"))
                .roles("USER")
                .build();

        UserDetails sup = User
                .withUsername("sup")
                .password(passwordEncoder().encode("sup"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(god, sup);
    }

    @Override
    public void run(String... args) {
        Set<com.example.demo72.user.User> users = Set.of(
                new com.example.demo72.user
                        .User("user", passwordEncoder().encode("pass"), "USER"),
                new com.example.demo72.user
                        .User("god", passwordEncoder().encode("god"), "ADMIN")
        );
        users.forEach(user -> {
            if (!userRepository.existsByUsername(user.getUsername())) {
                userRepository.save(user);
            }
        });
    }
}

