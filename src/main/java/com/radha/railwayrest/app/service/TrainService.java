package com.radha.railwayrest.app.service;

import com.radha.railwayrest.app.domain.Station;
import com.radha.railwayrest.app.domain.TimeTable;
import com.radha.railwayrest.app.domain.Train;
import com.radha.railwayrest.app.domain.TrainStop;
import com.radha.railwayrest.db.repo.TrainStopRepo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TrainService {
    private TimeTable timeTable;
    private TrainStopRepo trainStopRepo;
    private TrainStopService trainStopService;

    public TrainService(TimeTable timeTable, TrainStopRepo trainStopRepo, TrainStopService trainStopService) {
        this.timeTable = timeTable;
        this.trainStopRepo = trainStopRepo;
        this.trainStopService = trainStopService;
    }

    public List<Train> getTrains(String fromStationCode, String toStationCode) throws IOException, NoSuchFromStationException, NoSuchToStationException {
        return getTrains(fromStationCode, toStationCode, timeTable);

    }

    List<Train> getTrains(String fromStationCode, String toStationCode, TimeTable timeTable) throws NoSuchFromStationException, NoSuchToStationException {
       /* step 0:Declare a List as resultsList.
                Step 1: Get the fromStation object from the from station code using the stations map.
                Step 2:Get the list of trainstops(fromStationStops) object from the fromstation object.
                Step3:Get the toStation object from the  tostation code using the stations map.
        Step 4:Get the list of trainstops(toStationStops) object from the tostation object.
                step5:For each fromStationStop in fromStationStops do
            a)For each toStationsStop in the toStationsStops do
            i)if the fromsStationStop's train is equal toStationStop's train and fromsStationStop's
             sequenceNo is less than toStationStop's sequenceNo   then
        ii)Get the corresponding Train from the trainStopingAtFromStation  and add it to the resultsList.*/


        Map<String, Station> stations = timeTable.getStations();
        Station fromStation = stations.get(fromStationCode);
        if (fromStation == null) {
            throw new NoSuchFromStationException("The given fromStation code " + fromStationCode + " is not found");
        }
        ArrayList<TrainStop> fromStationStops = fromStation.getTrainStops();
        Station toStation = stations.get(toStationCode);
        if (toStation == null) {
            throw new NoSuchToStationException("The given toStation code " + toStationCode + " is not found");
        }
        List<TrainStop> toStationStops = toStation.getTrainStops();
        /*fromStationStops.stream()
                .forEach(fromStationStop -> resultTrains.addAll(getBeforeStopTrains(toStationStops, fromStationStop)));
        /*for (TrainStop fromStationStop : fromStationStops) {
             resultTrains.addAll(getBeforeStopTrains(toStationStops, fromStationStop));

        }*/
        return fromStationStops.stream()
                .flatMap(fromStationStop -> getBeforeStopTrains(toStationStops, fromStationStop))
                .collect(Collectors.toList());


    }

    private Stream<Train> getBeforeStopTrains(List<TrainStop> toStationStops, TrainStop fromStationStop){

        return toStationStops.stream()
                    .filter(toStationStop -> fromStationStop.isBeforeStop(toStationStop))
                    .map(TrainStop::getTrain);

    }


    List<Train> getTrainsBetweenSourceAndDestinationStation (String sourceStationCode, String destinationStationCode) {
        List<TrainStop> sourceStationTrainStops = trainStopService.getAllTrainStopsForStation(sourceStationCode);
        List<TrainStop> destinationStationTrainStops = trainStopService.getAllTrainStopsForStation(destinationStationCode);

        Map<Integer, TrainStop> sourceStationMap = sourceStationTrainStops.stream()
                .collect(Collectors.toMap(trainStop -> trainStop.getTrain().getId(), Function.identity()));

        return destinationStationTrainStops.stream()
                .filter(destinationStationTrainStop -> {
                    TrainStop sourceStationTrainStop = sourceStationMap.get(destinationStationTrainStop.getTrain().getId());
                    if(sourceStationTrainStop != null) {
                        if(sourceStationTrainStop.getSequence() < destinationStationTrainStop.getSequence()) {
                           return true;
                        }
                        return false;
                    }
                    return false;
                })
                .map(trainStop -> trainStop.getTrain())
                .collect(Collectors.toList());


    }
}


