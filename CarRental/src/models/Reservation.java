package models;

import models.enums.ReservationStatus;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private Vehicle vehicle;
    private User user;
    private LocalDateTime reservationTime;
    private LocalDateTime returnTime;
    private double cost;
    private boolean isPaid;
    private ReservationStatus status;

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", vehicle=" + vehicle +
                ", user=" + user +
                ", reservationTime=" + reservationTime +
                ", returnTime=" + returnTime +
                ", cost=" + cost +
                ", isPaid=" + isPaid +
                ", status=" + status +
                '}';
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }



    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
