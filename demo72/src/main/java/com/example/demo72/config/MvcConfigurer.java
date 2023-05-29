package com.example.demo72.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry){

        registry.addViewController("").setViewName("login");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/AdminDirectory").setViewName("/AdminDirectory");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/signup").setViewName("signup");
    }
}