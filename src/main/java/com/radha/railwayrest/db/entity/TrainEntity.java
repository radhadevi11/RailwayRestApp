package com.radha.railwayrest.db.entity;

import javax.persistence.*;

@Entity
public class TrainEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Integer id;
    @Column(name = "train_name")
    private String name;
    @Column(name = "train_number")
    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_station_id", nullable = false, updatable = false)
    private StationEntity sourceStation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_station_id", nullable = false, updatable = false)
    private StationEntity destinationStation;

    public TrainEntity() {
    }

    public TrainEntity(String name, String number, StationEntity sourceStation, StationEntity destinationStation) {
        this(null, name, number, sourceStation, destinationStation);
    }

    public TrainEntity(Integer id, String name, String number, StationEntity sourceStation, StationEntity destinationStation) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
    }

    public TrainEntity(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public StationEntity getSourceStation() {
        return sourceStation;
    }

    public StationEntity getDestinationStation() {
        return destinationStation;
    }



    public boolean equals(Object other) {
        if (other instanceof TrainEntity) {
            TrainEntity otherTrain = (TrainEntity) other;
            if (this.getNumber().equals(otherTrain.getNumber())) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Train{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", sourceStation=" + sourceStation +
                ", destinationStation=" + destinationStation +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}