package com.example.demo72.controllers;

import com.example.demo72.user.User;
import com.example.demo72.repository.UserRepository;
import com.example.demo72.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public String create(User user, Model model) {
        logger.info("user: {}", user);
        User savedUser = userService.register(user);
        logger.info("created user: {}", savedUser);
        return "redirect:/login";
    }
    @GetMapping("/signup")
    public String show(Model model) {
        logger.info("New user form");
        model.addAttribute("user", new User());
        return "signup";
    }
    @GetMapping("/user/{id}/view")
    public String view(@PathVariable Integer id, Model model) {
        logger.info("User with id: {}", id);
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/wiew";
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @GetMapping("/")
    public String returnToIndex() {
        return "login";
    }
}
