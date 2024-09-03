package service;

import models.Station;

import java.util.List;

public interface StationManager {
    Station addStation(String name, String location);

    Station getStation(Integer stationId);
    List<Station> getStations();
}
