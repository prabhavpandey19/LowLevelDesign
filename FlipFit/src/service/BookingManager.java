package service;

import models.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingManager {
    Booking createBooking(Integer userId, Integer slotId, LocalDate bookingDate);
    List<Booking> getBookingsForUser(Integer userId, LocalDate date);
}
