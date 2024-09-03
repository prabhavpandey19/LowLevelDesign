package service.impl;

import models.Reservation;
import models.User;
import models.Vehicle;
import models.enums.ReservationStatus;
import service.InventoryManagement;
import service.ReservationManager;
import service.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationManagerImpl implements ReservationManager {
    private static ReservationManagerImpl instance;

    private Integer counter;
    private InventoryManagement inventoryManagement;
    private UserManager userManager;

    private Map<Integer, Reservation> reservationIdVsReservation;
    private Map<Integer, List<Reservation>> userIdVsReservations;

    private ReservationManagerImpl() {
        counter = 0;
        inventoryManagement = InventoryManagementImpl.getInstance();
        userManager = UserManagerImpl.getInstance();
        reservationIdVsReservation = new HashMap<>();
        userIdVsReservations = new HashMap<>();
    }

    public static ReservationManagerImpl getInstance() {
        if (instance == null) {
            synchronized (ReservationManagerImpl.class) {
                if (instance == null) {
                    instance = new ReservationManagerImpl();
                }
            }
        }
        return instance;
    }

    private Integer getCounter() {
        synchronized (counter) {
            return counter++;
        }
    }

    @Override
    public Reservation bookReservation(Integer vehicleId, Integer stationId, Integer userId) {
        User user = userManager.getUser(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Vehicle vehicle = inventoryManagement.bookVehicle(vehicleId, stationId);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found");
        }
        Reservation reservation = new Reservation();
        reservation.setReservationId(getCounter());
        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setReservationTime(LocalDateTime.now());
        reservationIdVsReservation.put(reservation.getReservationId(), reservation);
        userIdVsReservations.putIfAbsent(userId, new ArrayList<>());
        userIdVsReservations.get(userId).add(reservation);
        return reservation;
    }

    @Override
    public Reservation cancelReservation(Integer reservationId) {
        return null;
    }

    @Override
    public Reservation completeReservation(Integer reservationId, Integer stationId) {
        Reservation reservation = reservationIdVsReservation.get(reservationId);
        if (reservation == null) {
            throw new RuntimeException("Reservation not found");
        }
        Vehicle vehicle = reservation.getVehicle();
        reservation.setReturnTime(LocalDateTime.now());
        reservation.setCost(calculate(vehicle.getPricing(), reservation.getReservationTime(), reservation.getReturnTime()));
//        reservation.isPaid(paymentService.charge(reservation.getUser(), reservation.getCost()); // payment service call for payment

        inventoryManagement.returnVehicle(vehicle.getId(), stationId);
        reservation.setStatus(ReservationStatus.COMPLETED);
        return reservation;
    }

    private double calculate(int pricing, LocalDateTime reservationTime, LocalDateTime returnTime) {
        int hours = returnTime.getHour() - reservationTime.getHour();
        return hours * pricing;
    }

    @Override
    public List<Reservation> userReservations(Integer userId) {
        return userIdVsReservations.get(userId);
    }
}
