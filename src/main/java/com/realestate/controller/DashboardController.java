package com.realestate.controller;

import com.realestate.entity.*;
import com.realestate.repository.UserRepository;
import com.realestate.service.BookingService;
import com.realestate.service.LeadService;
import com.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final PropertyService propertyService;
    private final BookingService bookingService;
    private final LeadService leadService;

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User current = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("currentUser", current);

        List<Property> allProperties = propertyService.getAll();
        model.addAttribute("totalProperties", allProperties.size());
        model.addAttribute("availableCount", allProperties.stream().filter(p -> p.getStatus() == PropertyStatus.AVAILABLE).count());
        model.addAttribute("bookedCount", allProperties.stream().filter(p -> p.getStatus() == PropertyStatus.BOOKED).count());
        model.addAttribute("soldRentedCount", allProperties.stream().filter(p -> p.getStatus() == PropertyStatus.SOLD || p.getStatus() == PropertyStatus.RENTED).count());

        if (current.getRole() == Role.ADMIN) {
            model.addAttribute("totalUsers", userRepository.findAll().size());
            model.addAttribute("totalBookings", bookingService.getAll().size());
            model.addAttribute("totalLeads", leadService.getAll().size());
            model.addAttribute("recentProperties", allProperties.stream().limit(5).toList());
        } else if (current.getRole() == Role.AGENT) {
            List<Property> myProps = propertyService.getByAgent(current.getId());
            model.addAttribute("myPropertiesCount", myProps.size());
            model.addAttribute("myLeads", leadService.getByAgent(current.getId()));
        } else {
            model.addAttribute("myBookings", bookingService.getByCustomer(current.getId()));
        }

        return "dashboard";
    }
}
