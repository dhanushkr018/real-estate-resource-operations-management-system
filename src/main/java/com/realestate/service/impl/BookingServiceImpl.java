package com.realestate.service.impl;

import com.realestate.entity.*;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.BookingRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public Booking createBooking(Long propertyId, Long customerId, BigDecimal amount) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Booking booking = Booking.builder()
                .property(property)
                .customer(customer)
                .bookingAmount(amount)
                .status(BookingStatus.PENDING)
                .build();

        property.setStatus(PropertyStatus.BOOKED);
        propertyRepository.save(property);

        return bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public Booking updateStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(status);

        if (status == BookingStatus.CONFIRMED) {
            Property p = booking.getProperty();
            p.setStatus(p.getPurpose() == PropertyPurpose.RENT ? PropertyStatus.RENTED : PropertyStatus.SOLD);
            propertyRepository.save(p);
        } else if (status == BookingStatus.CANCELLED) {
            Property p = booking.getProperty();
            p.setStatus(PropertyStatus.AVAILABLE);
            propertyRepository.save(p);
        }

        return bookingRepository.save(booking);
    }
}
