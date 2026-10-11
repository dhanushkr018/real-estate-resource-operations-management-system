package com.realestate.service.impl;

import com.realestate.entity.*;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.LeadRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeadServiceImplTest {

    @Mock LeadRepository leadRepository;
    @Mock PropertyRepository propertyRepository;
    @Mock UserRepository userRepository;
    @InjectMocks LeadServiceImpl service;

    @Test
    void createLead_savesNewLeadWithMessage() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(mock(Property.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(mock(User.class)));
        when(leadRepository.save(any(Lead.class))).thenAnswer(i -> i.getArgument(0));

        service.createLead(1L, 2L, "Is it still available?");

        ArgumentCaptor<Lead> captor = ArgumentCaptor.forClass(Lead.class);
        verify(leadRepository).save(captor.capture());
        assertEquals(LeadStatus.NEW, captor.getValue().getStatus());
        assertEquals("Is it still available?", captor.getValue().getMessage());
    }

    @Test
    void createLead_throwsWhenPropertyMissing() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.createLead(1L, 2L, "hi"));
        verify(leadRepository, never()).save(any());
    }

    @Test
    void createLead_throwsWhenCustomerMissing() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(mock(Property.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.createLead(1L, 2L, "hi"));
    }

    @Test
    void updateStatus_changesLeadStatus() {
        Lead lead = Lead.builder().status(LeadStatus.NEW).build();
        when(leadRepository.findById(3L)).thenReturn(Optional.of(lead));
        when(leadRepository.save(lead)).thenReturn(lead);

        Lead result = service.updateStatus(3L, LeadStatus.CONTACTED);

        assertEquals(LeadStatus.CONTACTED, result.getStatus());
    }

    @Test
    void updateStatus_throwsWhenLeadMissing() {
        when(leadRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateStatus(3L, LeadStatus.CONTACTED));
    }

    @Test
    void getByAgent_delegatesToRepository() {
        List<Lead> leads = List.of(mock(Lead.class));
        when(leadRepository.findByPropertyAgentId(7L)).thenReturn(leads);
        assertSame(leads, service.getByAgent(7L));
    }
}