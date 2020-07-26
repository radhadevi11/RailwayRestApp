package com.radha.railwayrest.app.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeTable {
    private ArrayList<Train> trains = new ArrayList<>();
    private HashMap<String, Station> stations = new HashMap<>();

    public TimeTable(ArrayList<Train> trains, HashMap<String, Station> stations) {
        this.trains = trains;
        this.stations = stations;
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public HashMap<String, Station> getStations() {
        return stations;
    }
}
