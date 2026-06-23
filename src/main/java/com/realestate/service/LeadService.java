package com.realestate.service;

import com.realestate.entity.Lead;
import java.util.List;

public interface LeadService {
    Lead createLead(Long propertyId, Long customerId, String message);
    List<Lead> getAll();
    List<Lead> getByAgent(Long agentId);
    Lead updateStatus(Long leadId, com.realestate.entity.LeadStatus status);
}
