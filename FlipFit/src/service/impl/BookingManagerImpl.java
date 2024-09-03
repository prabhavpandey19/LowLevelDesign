package service.impl;

import exception.FlipFitException;
import models.Booking;
import models.Slots;
import service.BookingManager;
import service.CenterManager;
import service.UserManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingManagerImpl implements BookingManager {
    private Integer bookingCounter;
    private static BookingManagerImpl bookingManager;
    private Map<Integer, Map<LocalDate,List<Booking>>> userIdVsSlots;
    private UserManager userManager;
    private CenterManager centerManager;

    private BookingManagerImpl() {
        bookingCounter = 0;
        userIdVsSlots = new HashMap<>();
        userManager = UserManagerImpl.getInstance();
        centerManager = CenterManagerImpl.getInstance();
    }

    public static BookingManagerImpl getInstance() {
        if (bookingManager == null) {
            synchronized (BookingManagerImpl.class) {
                if (bookingManager == null) {
                    bookingManager = new BookingManagerImpl();
                }
            }
        }
        return bookingManager;
    }

    private Integer getCounter() {
        synchronized (bookingCounter) {
            return bookingCounter++;
        }
    }

    @Override
    public Booking createBooking(Integer userId, Integer slotId, LocalDate bookingDate) {
        checkReBooking(userId, slotId, bookingDate);
        Slots slot = centerManager.getSlotById(slotId);
        if (slot.getCapacity() == 0) {
            throw new FlipFitException("Slot is full");
        }
        Booking booking = new Booking();
        booking.setBookingId(getCounter());
        booking.setSlot(slot);
        booking.setBookingDate(bookingDate);
        booking.setUser(userManager.getUser(userId));
        userIdVsSlots.putIfAbsent(userId, new HashMap<>());
        userIdVsSlots.get(userId).putIfAbsent(bookingDate, new ArrayList<>());
        userIdVsSlots.get(userId).get(bookingDate).add(booking);
        slot.setCapacity(slot.getCapacity() - 1);
        return booking;
    }

    @Override
    public List<Booking> getBookingsForUser(Integer userId, LocalDate date) {
        if (!userIdVsSlots.containsKey(userId)) {
            throw new FlipFitException("No bookings found for user");
        }
        return userIdVsSlots.get(userId).get(date);
    }

    private void checkReBooking(Integer userId, Integer slotId, LocalDate bookingDate) {
        if (userIdVsSlots.get(userId) != null) {
            List<Booking> bookings = userIdVsSlots.get(userId).get(bookingDate);
            if (bookings != null) {
                for (Booking booking : bookings) {
                    if (booking.getSlot().getSlotId() == slotId) {
                        throw new FlipFitException("User already booked for this slot");
                    }
                }
            }
        }
    }
}
