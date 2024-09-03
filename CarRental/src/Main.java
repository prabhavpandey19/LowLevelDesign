import models.Reservation;
import models.Station;
import models.User;
import models.Vehicle;
import models.enums.VehicleType;
import service.InventoryManagement;
import service.ReservationManager;
import service.StationManager;
import service.UserManager;
import service.impl.InventoryManagementImpl;
import service.impl.ReservationManagerImpl;
import service.impl.StationManagerImpl;
import service.impl.UserManagerImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InventoryManagement inventoryManagement = InventoryManagementImpl.getInstance();
        ReservationManager reservationManager = ReservationManagerImpl.getInstance();
        UserManager userManager = UserManagerImpl.getInstance();
        StationManager stationManager = StationManagerImpl.getInstance();
        registerTestUsers(userManager);

        setupInventory(inventoryManagement, stationManager);

        bookVehicleForUser1(inventoryManagement, reservationManager, userManager, stationManager);
        bookVehicleForUser2(inventoryManagement, reservationManager, userManager, stationManager);


        System.out.println("Hello world!");
    }

    private static void bookVehicleForUser2(InventoryManagement inventoryManagement, ReservationManager reservationManager, UserManager userManager, StationManager stationManager) {
        User user = userManager.login("Ravi@xyz", "user2", null, null);
        List<Station> stations = inventoryManagement.searchVehicles(VehicleType.SUV);
        System.out.println("Stations :- " + stations);
        System.out.println();
        List<Vehicle> vehicles = inventoryManagement.showVehicles(stations.get(0).getStationId(), VehicleType.SUV);

        System.out.println("Vehicles :- " + vehicles);
        System.out.println();
        Reservation reservation = reservationManager.bookReservation(vehicles.get(0).getId(), stations.get(0).getStationId(), user.getUserId());

        System.out.println("Reservations :- " + reservationManager.userReservations(user.getUserId()));
        System.out.println();

        reservationManager.completeReservation(reservation.getReservationId(), stationManager.getStations().get(1).getStationId());

        System.out.println("Reservations :- " + reservationManager.userReservations(user.getUserId()));
        System.out.println();

    }



    private static void bookVehicleForUser1(InventoryManagement inventoryManagement, ReservationManager reservationManager, UserManager userManager, StationManager stationManager) {
        User user = userManager.login(null, "user1", "1234567891", null);
        List<Station> stations = inventoryManagement.searchVehicles(VehicleType.SUV);
        System.out.println("Stations :- " + stations);
        System.out.println();
        List<Vehicle> vehicles = inventoryManagement.showVehicles(stations.get(1).getStationId(), VehicleType.SUV);

        System.out.println("Vehicles :- " + vehicles);
        System.out.println();
        Reservation reservation = reservationManager.bookReservation(vehicles.get(0).getId(), stations.get(1).getStationId(), user.getUserId());

        System.out.println("Reservations :- " + reservationManager.userReservations(user.getUserId()));
        System.out.println();

        reservationManager.completeReservation(reservation.getReservationId(), stations.get(0).getStationId());

        System.out.println("Reservations :- " + reservationManager.userReservations(user.getUserId()));
        System.out.println();

    }

    private static void setupInventory(InventoryManagement inventoryManagement, StationManager stationManager) {
        Station station1 = stationManager.addStation("Bellendur", "Bangalore");
        Station station2 = stationManager.addStation("Kormangala", "Bangalore");

        inventoryManagement.addVehicle("vin1", "make1", "model1", 2021, 1000, "licensePlate1", 100.0, VehicleType.SUV, station1.getStationId());
        inventoryManagement.addVehicle("vin2", "make2", "model2", 2021, 2000, "licensePlate2", 200.0, VehicleType.SEDAN, station1.getStationId());
        inventoryManagement.addVehicle("vin3", "make3", "model3", 2021, 3000, "licensePlate3", 300.0, VehicleType.HATCHBACK, station2.getStationId());

        inventoryManagement.addVehicle("vin4", "make4", "model4", 2021, 4000, "licensePlate4", 400.0, VehicleType.SUV, station2.getStationId());
        inventoryManagement.addVehicle("vin5", "make5", "model5", 2021, 5000, "licensePlate5", 500.0, VehicleType.SEDAN, station2.getStationId());
        inventoryManagement.addVehicle("vin6", "make6", "model6", 2021, 6000, "licensePlate6", 600.0, VehicleType.HATCHBACK, station1.getStationId());


    }

    private static void registerTestUsers(UserManager userManager) {
        userManager.createUser("Aashi", "Aashi@xyz", "user1", "1234567891", false);
        userManager.createUser("Ravi", "Ravi@xyz", "user2", "1234567880", false);
    }
}