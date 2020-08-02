package com.radha.railwayrest.app.service;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.app.domain.TimeTable;
import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class StationService {

    public Map<String, Station> getToStations(String fromStationCode) throws IOException, NoSuchFromStationException {
        return null;
    }
    Map<String, Station> getToStations(String fromStationCode, TimeTable timeTable) throws NoSuchFromStationException {
          /* Algorithm:
        Step1:Declare a TimeTable object as timeTable and a map as destinationStations.
        Step2:Get the fromStation object from the stations map in the timeTable using the fromStationCode as key.
        Step3:for each trainStop in the fromStation object do
            i)Get the train from the trainStop
            ii)For each trainStop in the train do
            a)Get the Station and put it into the destinationStations

        return null;*/
        System.out.println("Getting to stations for fromStationdCode:" + fromStationCode);
        Map<String, Station> destinationStations = new HashMap();
        Map<String, Station> stations = timeTable.getStations();
        Station fromStation = stations.get(fromStationCode);
        if (fromStation == null) {
            throw new NoSuchFromStationException("");
        }
        for (TrainStop trainStop : fromStation.getTrainStops()) {
            int sequence = trainStop.getSequence();
            Train stoppingTrain = trainStop.getTrain();
            List<Station> stopingStations = stoppingTrain.getStoppingStations(sequence);
            for (Station stoppingStation : stopingStations) {
                destinationStations.put(stoppingStation.getCode(), stoppingStation);
            }
        }
        fromStation.getTrainStops().stream()
                .forEach(trainStop ->destinationStations.putAll(getDestinationStation(trainStop.getTrain(),trainStop.getSequence())));
        return destinationStations;
    }

    private Map<String, Station> getDestinationStation(Train stoppingTrain, int sequence){
        List<Station> stopingStations = stoppingTrain.getStoppingStations(sequence);
        return stopingStations.stream()
                .collect(Collectors.toMap(Station::getCode, Function.identity()));

    }


    public Map<String, Station> getFromStations() throws IOException {
        return null;
    }
}
