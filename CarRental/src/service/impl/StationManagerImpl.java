package service.impl;

import models.Station;
import service.StationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationManagerImpl implements StationManager {

    private Map<Integer, Station> stationIdVsStation;
    private Integer counter;
    private Map<String, Station> stationNameVsStation;
    private List<Station> stations;
    private static StationManagerImpl instance;

    private StationManagerImpl() {
        counter = 0;
        stationIdVsStation = new HashMap<>();
        stationNameVsStation = new HashMap<>();
        stations = new ArrayList<>();
    }

    private Integer getCounter() {
        synchronized (counter) {
            return counter++;
        }
    }

    public static StationManagerImpl getInstance() {
        if (instance == null) {
            synchronized (StationManagerImpl.class) {
                if (instance == null) {
                    instance = new StationManagerImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Station addStation(String name, String location) {
        Station station = new Station();
        station.setStationId(getCounter());
        station.setStationName(name);
        station.setStationLocation(location);
        stationIdVsStation.put(station.getStationId(), station);
        stationNameVsStation.put(station.getStationName(), station);
        stations.add(station);
        return station;
    }

    @Override
    public Station getStation(Integer stationId) {
        if (!stationIdVsStation.containsKey(stationId)) {
            throw new RuntimeException("Station not found");
        }
        return stationIdVsStation.get(stationId);
    }

    @Override
    public List<Station> getStations() {
        return stations;
    }
}
