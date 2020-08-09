
package com.radha.railwayrest.app.domain;

import java.util.ArrayList;

public class Station {

    private Integer id;
    private String name;
    private String code;
    private ArrayList<TrainStop> trainStops = new ArrayList<>();
    private double latitude;
    private double longitude;


    public Station(String name, String code, double latitude, double longitude) {
        this(null, name, code, latitude, longitude);
    }

    public Station(Integer id, String name, String code, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
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
        return "StationName:"+this.name+" StationCode:"+this.code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}