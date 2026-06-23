package com.realestate.service;

import com.realestate.entity.Booking;
import java.util.List;

public interface BookingService {
    Booking createBooking(Long propertyId, Long customerId, java.math.BigDecimal amount);
    List<Booking> getAll();
    List<Booking> getByCustomer(Long customerId);
    Booking updateStatus(Long bookingId, com.realestate.entity.BookingStatus status);
}
