package com.realestate.service.impl;

import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @InjectMocks UserServiceImpl service;

    @Test
    void register_encodesPasswordBeforeSaving() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("a@b.com");
        when(user.getPassword()).thenReturn("plain");
        when(userRepository.existsByEmail("a@b.com")).thenReturn(false);
        when(passwordEncoder.encode("plain")).thenReturn("hashed");
        when(userRepository.save(user)).thenReturn(user);

        service.register(user);

        verify(user).setPassword("hashed");
        verify(userRepository).save(user);
    }

    @Test
    void register_rejectsDuplicateEmail() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("a@b.com");
        when(userRepository.existsByEmail("a@b.com")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> service.register(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    void getById_throwsWhenMissing() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void getByRole_delegatesToRepository() {
        Role role = Role.values()[0];
        List<User> users = List.of(mock(User.class));
        when(userRepository.findByRole(role)).thenReturn(users);
        assertSame(users, service.getByRole(role));
    }
}