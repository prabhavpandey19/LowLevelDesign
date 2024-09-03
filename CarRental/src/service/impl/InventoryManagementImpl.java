package service.impl;

import models.Station;
import models.Vehicle;
import models.enums.Status;
import models.enums.VehicleType;
import service.InventoryManagement;
import service.StationManager;

import java.util.*;

public class InventoryManagementImpl implements InventoryManagement {
    
    private static InventoryManagementImpl instance;
    private Map<Integer, List<Vehicle>> stationIdVsVehicles;
    private Map<Integer, Vehicle> vehicleIdVsVehicle;
    private Integer counter;
    private StationManager stationManager;
    private InventoryManagementImpl() {
        counter = 0;
        stationIdVsVehicles = new HashMap<>();
        vehicleIdVsVehicle = new HashMap<>();
        stationManager = StationManagerImpl.getInstance();
    }

    public static InventoryManagementImpl getInstance() {
        if (instance == null) {
            synchronized (InventoryManagementImpl.class) {
                if (instance == null) {
                    instance = new InventoryManagementImpl();
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
    public Vehicle addVehicle(String vin, String make, String model, Integer year, Integer price, String licensePlate, Double odometer,VehicleType vehicleType, Integer stationId) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(getCounter());
        vehicle.setVin(vin);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setYear(year);
        vehicle.setPricing(price);
        vehicle.setLicensePlate(licensePlate);
        vehicle.setOdometer(odometer);
        vehicle.setVehicleType(vehicleType);
        vehicle.setStationId(stationId);
        vehicle.setStatus(Status.AVAILABLE);
        vehicleIdVsVehicle.put(vehicle.getId(), vehicle);
        stationIdVsVehicles.putIfAbsent(stationId, new ArrayList<>());
        stationIdVsVehicles.get(stationId).add(vehicle);
        return vehicle;
    }

    @Override
    public List<Vehicle> showVehicles(Integer stationId, VehicleType vehicleType) {
        return stationIdVsVehicles.get(stationId).stream().filter(vehicle -> vehicle.getVehicleType() == vehicleType).toList();
    }

    @Override
    public Vehicle getVehicle(Integer vehicleId) {
        return vehicleIdVsVehicle.get(vehicleId);
    }

    @Override
    public Vehicle bookVehicle(Integer vehicleId, Integer stationId) {
        List<Vehicle> vehicles = stationIdVsVehicles.get(stationId);
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == vehicleId) {
                vehicle.setStatus(Status.IN_RESERVATION);
                vehicles.remove(vehicle);
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public Vehicle returnVehicle(Integer vehicleId, Integer stationId) {
        List<Vehicle> vehicles = stationIdVsVehicles.get(stationId);
        Vehicle vehicle = vehicleIdVsVehicle.get(vehicleId);
        vehicle.setStatus(Status.AVAILABLE);
        vehicles.add(vehicle);
        vehicle.setStationId(stationId);
        return vehicle;
    }

    @Override
    public List<Station> searchVehicles(VehicleType vehicleType) {
        List<Station> stations = stationManager.getStations();
        List<Vehicle> foundVehicles = new ArrayList<>();
        for (Station station : stations) {
            List<Vehicle> vehicles = stationIdVsVehicles.get(station.getStationId());
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getVehicleType() == vehicleType) {
                    foundVehicles.add(vehicle);
                }
            }
        }
        Collections.sort(foundVehicles, (a, b) -> a.getPricing() - b.getPricing());
        List<Station> result = new ArrayList<>();
        Set<Integer> checkedStationIds = new HashSet<>();

        for (Vehicle vehicle : foundVehicles) {
            if (checkedStationIds.contains(vehicle.getStationId())) {
                continue;
            }
            result.add(stationManager.getStation(vehicle.getStationId()));
            checkedStationIds.add(vehicle.getStationId());
        }
        return result;

    }
}
