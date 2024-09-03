package service;

import models.Station;
import models.Vehicle;
import models.enums.VehicleType;

import java.util.List;

public interface InventoryManagement {
    Vehicle addVehicle(String vin, String make, String model, Integer year, Integer price, String licensePlate, Double odometer, VehicleType vehicleType, Integer stationId);
    List<Vehicle> showVehicles(Integer stationId, VehicleType vehicleType);
    Vehicle getVehicle(Integer vehicleId);
    Vehicle bookVehicle(Integer vehicleId, Integer stationId);
    Vehicle returnVehicle(Integer vehicleId, Integer stationId);
    List<Station> searchVehicles(VehicleType vehicleType);
}
