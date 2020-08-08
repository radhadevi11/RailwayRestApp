package com.radha.railwayrest.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "train_stop")
public class TrainStopEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "train_stop_id")
    private Integer id;
    @Column(name = "arrival_time")
    private String arrivalTime;
    @Column(name = "departure_time")
    private String departureTime;
    @ManyToOne(optional = false)
    @JoinColumn(name = "train_id", nullable = false, updatable = false)
    private TrainEntity train;
    @Column(name = "sequence")
    private int sequence;
    @ManyToOne(optional = false)
    @JoinColumn(name = "station_id", nullable = false, updatable = false)
    private StationEntity station;

    public TrainStopEntity() {
    }

    public TrainStopEntity(String arrivalTime, String departureTime, TrainEntity train, int sequence, StationEntity station) {
        this(null, arrivalTime, departureTime, train, sequence, station);
    }

    public TrainStopEntity(Integer id, String arrivalTime, String departureTime, TrainEntity train, int sequence, StationEntity station) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.train = train;
        this.sequence = sequence;
        this.station = station;


    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public TrainEntity getTrain() {
        return train;
    }

    public int getSequence() {
        return sequence;
    }

    public StationEntity getStation() {
        return station;
    }


    public boolean isBeforeStop(TrainStopEntity otherStop){
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
                '}';
    }

    public boolean equals(Object other){
        if(other instanceof TrainStopEntity){
            return (this.getTrain().equals(((TrainStopEntity) other).getTrain())
                    && this.getStation().equals(((TrainStopEntity) other).getStation()));
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