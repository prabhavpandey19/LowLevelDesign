package service;

import models.Reservation;

import java.util.List;

public interface ReservationManager {
    Reservation bookReservation(Integer vehicleId, Integer stationId, Integer userId);
    Reservation cancelReservation(Integer reservationId);
    Reservation completeReservation(Integer reservationId, Integer stationId);
    List<Reservation> userReservations(Integer userId);
}
