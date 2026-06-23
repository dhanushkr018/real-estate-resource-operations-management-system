package com.realestate.controller;

import com.realestate.dto.RegisterRequest;
import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "register";
        }
        try {
            User user = User.builder()
                    .fullName(registerRequest.getFullName())
                    .email(registerRequest.getEmail())
                    .password(registerRequest.getPassword())
                    .phone(registerRequest.getPhone())
                    .role(registerRequest.getRole())
                    .enabled(true)
                    .build();
            userService.register(user);
            return "redirect:/login?registered";
        } catch (IllegalStateException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("roles", Role.values());
            return "register";
        }
    }
}
