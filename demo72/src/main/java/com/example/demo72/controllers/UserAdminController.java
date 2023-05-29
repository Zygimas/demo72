package com.example.demo72.controllers;

import com.example.demo72.user.User;
import com.example.demo72.user.UserDetailsMapper;
import com.example.demo72.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAdminController {

    private final UserService userService;

    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/AdminDirectory")
    public String showAdminDirectory(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "AdminDirectory";
    }

    @PostMapping("/change-role/{userId}/{newRole}")
    public String changeUserRole(@PathVariable Integer userId, @PathVariable String newRole) {
        userService.changeUserRole(userId, newRole);
        return "redirect:/AdminDirectory";
    }
    @PostMapping("/change-role")
    public String changeRole(@RequestParam("role") String role) {
        UserDetailsMapper userDetails = (UserDetailsMapper) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();

        if (!user.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/error";
        }

        user.setRole(role);
        userService.save(user);

        return "redirect:/AdminDirectory?success";
    }

    @PostMapping("/delete-user/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return "redirect:/AdminDirectory";
    }
}