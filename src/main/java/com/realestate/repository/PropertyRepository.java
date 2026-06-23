package com.realestate.repository;

import com.realestate.entity.Property;
import com.realestate.entity.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByStatus(PropertyStatus status);
    List<Property> findByCityContainingIgnoreCase(String city);
    List<Property> findByAgentId(Long agentId);
    List<Property> findByTitleContainingIgnoreCaseOrCityContainingIgnoreCase(String title, String city);
}
