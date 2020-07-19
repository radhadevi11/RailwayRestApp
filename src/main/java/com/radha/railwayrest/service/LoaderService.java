package com.radha.railwayrest.service;

import com.radha.railwayrest.*;
import com.radha.railwayrest.dao.StationDaoImpl;
import com.radha.railwayrest.dao.TrainDaoImpl;
import com.radha.railwayrest.dao.TrainStopDaoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoaderService {
   private StationDaoImpl stationDao;
   private TrainDaoImpl trainDao;
   private TrainStopDaoImpl trainStopDao;

    public LoaderService(StationDaoImpl stationDao, TrainDaoImpl trainDao, TrainStopDaoImpl trainStopDao) {
        this.stationDao = stationDao;
        this.trainDao = trainDao;
        this.trainStopDao = trainStopDao;
    }

/*Algorithm
Chennai - santragachi(1-12)(each station of the train is a train stop)
Step0.1:declare a Train object(currentTrain) and
declare stations map with the key of stationCode and value of Station object and
initialize a trains hashMap with the key of trainNo and value of Train object.
    For each Line in the File do
            Store the stationCode in a stationCode variable
            if the stationCode is not present in the stations map then
                   Create a Station.
                   Store the station code and Station into the map.
                   save station to database
            Store train number in trainNo variable
            if trainNo is not present in trains map then
                    Create a sourceStation
                    Create a Destination Station
                    Create a Train and store it into the currentTrain
                    save currentTrain to database
                    add this trainNo and train object to trains map

        Create a TrainStop
        add TrainStop to database

*/

    public void load(BufferedReader myReader, Map<String, LatLng> latLngMap) throws IOException {
        Map<String, Station> stations = new HashMap<>();
        Map<String, Train> trains = new HashMap<>();
        String readText;
        myReader.readLine();
        while ((readText= myReader.readLine()) != null) {

           saveOneRow(stations, trains, readText);

        }
    }

     Station saveStation(String stationName, String stationCode, Map<String, Station> stations){
        Station currentStation = stations.get(stationCode);
        if (! stations.containsKey(stationCode) ) {
            currentStation = new Station(stationName, stationCode);
            stationDao.save(currentStation);
            stations.put(stationCode,currentStation);
        }
        return currentStation;
    }

    Train saveTrain(Map<String, Train> trains, TimeTableEntry timeTableEntry,
                    Map<String, Station> stations){
        String trainNo = timeTableEntry.getTrainNo();
        Train currentTrain = trains.get(trainNo);
        if(! trains.containsKey(trainNo)){
            //to check whether this station is available in the station table
            Station sourceStation = saveStation(timeTableEntry.getSourceStationName(),
                    timeTableEntry.getSourceStationCode(),stations);
            Station destinationStation = saveStation(timeTableEntry.getDestinationStationName(),
                    timeTableEntry.getDestinationStationCode(),stations);

            currentTrain = new Train(timeTableEntry.getTrainName(), trainNo, sourceStation, destinationStation);
            trains.put(trainNo,currentTrain);
            trainDao.save(currentTrain);

        }
        return currentTrain;
    }

     void saveOneRow(Map<String, Station> stations, Map<String, Train> trains, String readText ){
        TimeTableEntry timeTableEntry = getTimeTableEntry(readText);

        Station currentStation = saveStation(timeTableEntry.getStationName(), timeTableEntry.getStationCode(),
                stations);
        Train currentTrain = saveTrain(trains, timeTableEntry, stations);

        saveTrainStop(timeTableEntry, currentStation, currentTrain);

    }

    void saveTrainStop(TimeTableEntry timeTableEntry, Station currentStation, Train currentTrain) {
        TrainStop trainStop = new TrainStop(timeTableEntry.getArrivalTime(),timeTableEntry.getDepartureTime(),
                currentTrain,timeTableEntry.getSequence(),currentStation,timeTableEntry.getDistance());
        trainStopDao.save(trainStop);
    }

    TimeTableEntry getTimeTableEntry(String readText) {
        String splitText[] = readText.split(",");

        return new TimeTableEntry(splitText);
    }


}
