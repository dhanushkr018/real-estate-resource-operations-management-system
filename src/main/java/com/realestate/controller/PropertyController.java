package com.realestate.controller;

import com.realestate.entity.*;
import com.realestate.repository.UserRepository;
import com.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;
    private final UserRepository userRepository;

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("properties", propertyService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "properties/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("property", propertyService.getById(id));
        return "properties/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("property", Property.builder().build());
        model.addAttribute("types", PropertyType.values());
        model.addAttribute("purposes", PropertyPurpose.values());
        model.addAttribute("statuses", PropertyStatus.values());
        return "properties/form";
    }

    @PostMapping
    public String create(@ModelAttribute Property property, Authentication authentication) {
        User agent = userRepository.findByEmail(authentication.getName()).orElseThrow();
        property.setAgent(agent);
        propertyService.save(property);
        return "redirect:/properties";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("property", propertyService.getById(id));
        model.addAttribute("types", PropertyType.values());
        model.addAttribute("purposes", PropertyPurpose.values());
        model.addAttribute("statuses", PropertyStatus.values());
        return "properties/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Property property) {
        propertyService.update(id, property);
        return "redirect:/properties";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        propertyService.delete(id);
        return "redirect:/properties";
    }
}
