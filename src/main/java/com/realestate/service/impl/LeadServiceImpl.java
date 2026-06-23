package com.realestate.service.impl;

import com.realestate.entity.*;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.LeadRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public Lead createLead(Long propertyId, Long customerId, String message) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Lead lead = Lead.builder()
                .property(property)
                .customer(customer)
                .message(message)
                .status(LeadStatus.NEW)
                .build();

        return leadRepository.save(lead);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lead> getAll() {
        return leadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lead> getByAgent(Long agentId) {
        return leadRepository.findByPropertyAgentId(agentId);
    }

    @Override
    public Lead updateStatus(Long leadId, LeadStatus status) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));
        lead.setStatus(status);
        return leadRepository.save(lead);
    }
}
