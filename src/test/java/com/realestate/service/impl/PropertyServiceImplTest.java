package com.realestate.service.impl;

import com.realestate.entity.Property;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @Mock PropertyRepository propertyRepository;
    @InjectMocks PropertyServiceImpl service;

    @Test
    void getById_returnsPropertyWhenFound() {
        Property property = mock(Property.class);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        assertSame(property, service.getById(1L));
    }

    @Test
    void getById_throwsWhenMissing() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void search_nullOrBlankReturnsEverything() {
        List<Property> all = List.of(mock(Property.class));
        when(propertyRepository.findAll()).thenReturn(all);
        assertSame(all, service.search(null));
        assertSame(all, service.search("   "));
    }

    @Test
    void search_keywordMatchesTitleOrCity() {
        List<Property> found = List.of(mock(Property.class));
        when(propertyRepository.findByTitleContainingIgnoreCaseOrCityContainingIgnoreCase("pune", "pune"))
                .thenReturn(found);
        assertSame(found, service.search("pune"));
    }
    
@Test
void search_trimsKeywordBeforeSearching() {
    List<Property> found = List.of(mock(Property.class));

    when(propertyRepository
            .findByTitleContainingIgnoreCaseOrCityContainingIgnoreCase("pune", "pune"))
            .thenReturn(found);

    assertSame(found, service.search("  pune  "));

    verify(propertyRepository)
            .findByTitleContainingIgnoreCaseOrCityContainingIgnoreCase("pune", "pune");
}


    @Test
    void update_copiesFieldsOntoExistingProperty() {
        Property existing = mock(Property.class);
        Property updated = mock(Property.class);
        when(updated.getTitle()).thenReturn("New title");
        when(updated.getCity()).thenReturn("Bengaluru");
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(propertyRepository.save(existing)).thenReturn(existing);

        Property result = service.update(1L, updated);

        verify(existing).setTitle("New title");
        verify(existing).setCity("Bengaluru");
        assertSame(existing, result);
    }

    @Test
    void delete_removesExistingProperty() {
        Property existing = mock(Property.class);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(existing));
        service.delete(1L);
        verify(propertyRepository).delete(existing);
    }

    @Test
    void getByAgent_delegatesToRepository() {
        List<Property> list = List.of(mock(Property.class));
        when(propertyRepository.findByAgentId(4L)).thenReturn(list);
        assertSame(list, service.getByAgent(4L));
    }
}
