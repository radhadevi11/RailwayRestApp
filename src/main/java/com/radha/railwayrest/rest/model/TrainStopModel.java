package com.radha.railwayrest.rest.model;

public class TrainStopModel {
    private String arrivalTime;
    private String departureTime;
    private int sequence;
    private StationModel station;
    private long distance;

    public TrainStopModel(String arrivalTime, String departureTime, int sequence, StationModel station, long distance) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.sequence = sequence;
        this.station = station;
        this.distance = distance;

    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getSequence() {
        return sequence;
    }

    public StationModel getStation() {
        return station;
    }

    public long getDistance() {
        return distance;
    }


    @Override
    public String toString() {
        return "TrainStop{" +
                "arrivalTime='" + arrivalTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", sequence=" + sequence +
                ", station=" + station.getCode() +
                ", distance=" + distance +
                '}';
    }
}