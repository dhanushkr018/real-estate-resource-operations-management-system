package com.realestate.service.impl;

import com.realestate.entity.Property;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.PropertyRepository;
import com.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Override
    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Property update(Long id, Property updated) {
        Property existing = getById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setType(updated.getType());
        existing.setPurpose(updated.getPurpose());
        existing.setPrice(updated.getPrice());
        existing.setLocation(updated.getLocation());
        existing.setCity(updated.getCity());
        existing.setBedrooms(updated.getBedrooms());
        existing.setBathrooms(updated.getBathrooms());
        existing.setAreaSqft(updated.getAreaSqft());
        existing.setStatus(updated.getStatus());
        existing.setImageUrl(updated.getImageUrl());
        return propertyRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        propertyRepository.delete(getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Property getById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return getAll();
        return propertyRepository.findByTitleContainingIgnoreCaseOrCityContainingIgnoreCase(keyword, keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Property> getByAgent(Long agentId) {
        return propertyRepository.findByAgentId(agentId);
    }
}
