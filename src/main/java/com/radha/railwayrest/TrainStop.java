package com.radha.railwayrest;

public class TrainStop {
    private Integer id;
    private String arrivalTime;
    private String departureTime;
    private Train train;
    private int sequence;
    private Station station;
    private long distance;

    public TrainStop(String arrivalTime, String departureTime, Train train, int sequence, Station station, long distance) {
        this(null, arrivalTime, departureTime, train, sequence, station, distance);
    }

    public TrainStop(Integer id, String arrivalTime, String departureTime, Train train, int sequence, Station station, long distance) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.train = train;
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

    public Train getTrain() {
        return train;
    }

    public int getSequence() {
        return sequence;
    }

    public Station getStation() {
        return station;
    }

    public long getDistance() {
        return distance;
    }

    public boolean isBeforeStop(TrainStop otherStop){
        if(this.getTrain().equals(otherStop.getTrain()) && this.getSequence() < otherStop.getSequence()){
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public String toString() {
        return "TrainStop{" +
                "arrivalTime='" + arrivalTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", train=" + train.getNumber() +
                ", sequence=" + sequence +
                ", station=" + station.getCode() +
                ", distance=" + distance +
                '}';
    }

    public boolean equals(Object other){
        if(other instanceof TrainStop){
            return (this.getTrain().equals(((TrainStop) other).getTrain())
                    && this.getStation().equals(((TrainStop) other).getStation()));
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}