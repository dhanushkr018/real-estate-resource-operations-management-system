package com.realestate.service.impl;

import com.realestate.entity.*;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.BookingRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock BookingRepository bookingRepository;
    @Mock PropertyRepository propertyRepository;
    @Mock UserRepository userRepository;
    @InjectMocks BookingServiceImpl service;

    @Test
    void createBooking_marksPropertyBookedAndSavesPendingBooking() {
        Property property = mock(Property.class);
        User customer = mock(User.class);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(userRepository.findById(2L)).thenReturn(Optional.of(customer));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        service.createBooking(1L, 2L, new BigDecimal("5000"));

        verify(property).setStatus(PropertyStatus.BOOKED);
        verify(propertyRepository).save(property);
        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(captor.capture());
        assertEquals(BookingStatus.PENDING, captor.getValue().getStatus());
        assertEquals(new BigDecimal("5000"), captor.getValue().getBookingAmount());
    }

    @Test
    void createBooking_throwsWhenPropertyMissing() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.createBooking(1L, 2L, BigDecimal.ONE));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBooking_throwsWhenCustomerMissing() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(mock(Property.class)));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.createBooking(1L, 2L, BigDecimal.ONE));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void updateStatus_confirmedRentalBecomesRented() {
        Property property = mock(Property.class);
        when(property.getPurpose()).thenReturn(PropertyPurpose.RENT);
        Booking booking = Booking.builder().property(property).status(BookingStatus.PENDING).build();
        when(bookingRepository.findById(5L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = service.updateStatus(5L, BookingStatus.CONFIRMED);

        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
        verify(property).setStatus(PropertyStatus.RENTED);
        verify(propertyRepository).save(property);
    }

    @Test
    void updateStatus_confirmedNonRentalBecomesSold() {
        Property property = mock(Property.class);
        PropertyPurpose notRent = Arrays.stream(PropertyPurpose.values())
                .filter(p -> p != PropertyPurpose.RENT).findFirst().orElseThrow();
        when(property.getPurpose()).thenReturn(notRent);
        Booking booking = Booking.builder().property(property).status(BookingStatus.PENDING).build();
        when(bookingRepository.findById(5L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        service.updateStatus(5L, BookingStatus.CONFIRMED);

        verify(property).setStatus(PropertyStatus.SOLD);
    }

    @Test
    void updateStatus_cancelledMakesPropertyAvailableAgain() {
        Property property = mock(Property.class);
        Booking booking = Booking.builder().property(property).status(BookingStatus.PENDING).build();
        when(bookingRepository.findById(5L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking result = service.updateStatus(5L, BookingStatus.CANCELLED);

        assertEquals(BookingStatus.CANCELLED, result.getStatus());
        verify(property).setStatus(PropertyStatus.AVAILABLE);
    }

    @Test
    void updateStatus_throwsWhenBookingMissing() {
        when(bookingRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateStatus(9L, BookingStatus.CONFIRMED));
    }
}