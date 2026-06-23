package com.realestate.controller;

import com.realestate.entity.BookingStatus;
import com.realestate.entity.User;
import com.realestate.repository.UserRepository;
import com.realestate.service.BookingService;
import com.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final PropertyService propertyService;
    private final UserRepository userRepository;

    @GetMapping
    public String list(Authentication authentication, Model model) {
        User current = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (current.getRole().name().equals("ADMIN") || current.getRole().name().equals("AGENT")) {
            model.addAttribute("bookings", bookingService.getAll());
        } else {
            model.addAttribute("bookings", bookingService.getByCustomer(current.getId()));
        }
        return "bookings/list";
    }

    @PostMapping("/create/{propertyId}")
    public String book(@PathVariable Long propertyId, Authentication authentication) {
        User customer = userRepository.findByEmail(authentication.getName()).orElseThrow();
        var property = propertyService.getById(propertyId);
        bookingService.createBooking(propertyId, customer.getId(), property.getPrice().multiply(BigDecimal.valueOf(0.1)));
        return "redirect:/bookings";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        bookingService.updateStatus(id, status);
        return "redirect:/bookings";
    }
}
