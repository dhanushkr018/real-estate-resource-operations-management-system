package com.realestate.repository;

import com.realestate.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByCustomerId(Long customerId);
    List<Lead> findByPropertyAgentId(Long agentId);
}
