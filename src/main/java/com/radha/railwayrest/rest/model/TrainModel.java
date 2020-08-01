package com.radha.railwayrest.rest.model;

import java.util.List;

public class TrainModel {
    private String name;
    private String number;
    private StationModel sourceStation;
    private StationModel destinationStation;
    private List<TrainStopModel> trainStops;


    public TrainModel(String name, String number, StationModel sourceStation, StationModel destinationStation, List<TrainStopModel> trainStops) {
        this.name = name;
        this.number = number;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.trainStops = trainStops;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public StationModel getSourceStation() {
        return sourceStation;
    }

    public StationModel getDestinationStation() {
        return destinationStation;
    }

    public List<TrainStopModel> getTrainStops() {
        return trainStops;
    }

    public boolean equals(Object other) {
        if (other instanceof TrainModel) {
            TrainModel otherTrain = (TrainModel) other;
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
                ", trainStops=" + trainStops +
                '}';
    }
}