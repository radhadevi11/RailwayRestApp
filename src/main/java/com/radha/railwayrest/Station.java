
package com.radha.railwayrest;

import java.util.ArrayList;

public class Station{
    private Integer id;
    private String name;
    private String code;
    private ArrayList<TrainStop> trainStops = new ArrayList<>();
    private LatLng latLng;

    public Station(String name, String code, LatLng latLng) {
        this.name = name;
        this.code = code;
        this.latLng = latLng;

    }

    public Station(String name, String code) {
        this(null, name, code);
    }

    public Station(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Station(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<TrainStop> getTrainStops() {
        return trainStops;
    }

    public void addTrainStop(TrainStop stop) {
        trainStops.add(stop);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public ArrayList<Train> getStoppingTrains() {
        ArrayList<Train> trains = new ArrayList<>();
        for(TrainStop trainStop: trainStops ) {
            trains.add(trainStop.getTrain());
        }
        return trains;
    }
    public boolean equals(Object other) {
        if (other instanceof Station) {
            Station otherStation = (Station) other;
            if (this.getCode().equals(otherStation.getCode())) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    public String toString(){
        return "StationName:"+this.name+" StationCode:"+this.code+this.latLng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}