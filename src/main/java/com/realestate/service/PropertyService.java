package com.realestate.service;

import com.realestate.entity.Property;
import java.util.List;

public interface PropertyService {
    Property save(Property property);
    Property update(Long id, Property property);
    void delete(Long id);
    Property getById(Long id);
    List<Property> getAll();
    List<Property> search(String keyword);
    List<Property> getByAgent(Long agentId);
}
