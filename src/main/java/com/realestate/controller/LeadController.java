package com.realestate.controller;

import com.realestate.entity.LeadStatus;
import com.realestate.entity.User;
import com.realestate.repository.UserRepository;
import com.realestate.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/leads")
public class LeadController {

    private final LeadService leadService;
    private final UserRepository userRepository;

    @GetMapping
    public String list(Authentication authentication, Model model) {
        User current = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (current.getRole().name().equals("ADMIN")) {
            model.addAttribute("leads", leadService.getAll());
        } else if (current.getRole().name().equals("AGENT")) {
            model.addAttribute("leads", leadService.getByAgent(current.getId()));
        } else {
            model.addAttribute("leads", leadService.getAll().stream()
                    .filter(l -> l.getCustomer().getId().equals(current.getId())).toList());
        }
        return "leads/list";
    }

    @PostMapping("/create/{propertyId}")
    public String createLead(@PathVariable Long propertyId, @RequestParam String message, Authentication authentication) {
        User customer = userRepository.findByEmail(authentication.getName()).orElseThrow();
        leadService.createLead(propertyId, customer.getId(), message);
        return "redirect:/properties/" + propertyId;
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam LeadStatus status) {
        leadService.updateStatus(id, status);
        return "redirect:/leads";
    }
}
