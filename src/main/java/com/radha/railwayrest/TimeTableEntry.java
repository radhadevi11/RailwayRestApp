package com.radha.railwayrest;

public class TimeTableEntry {
   private String trainNo;
   private String trainName;
   private int sequence;
   private String stationCode;
   private String stationName;
   private String arrivalTime;
   private String departureTime;
   private long distance;
   private String sourceStationCode;
   private String sourceStationName;
   private String destinationStationCode;
   private String destinationStationName;

    public TimeTableEntry(String trainNo, String trainName, int sequence, String stationCode, String stationName,
                          String arrivalTime, String departureTime, long distance, String sourceStationCode,
                          String sourceStationName, String destinationStationCode, String destinationStationName) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.sequence = sequence;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.distance = distance;
        this.sourceStationCode = sourceStationCode;
        this.sourceStationName = sourceStationName;
        this.destinationStationCode = destinationStationCode;
        this.destinationStationName = destinationStationName;
    }
    public TimeTableEntry(String[]entry){
        this(entry[0],entry[1],Integer.parseInt(entry[2]),
                entry[3],entry[4],entry[5],entry[6],Long.parseLong(entry[7]),
                entry[8],entry[9],entry[10], entry[11]);
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getTrainName() {
        return trainName;
    }

    public int getSequence() {
        return sequence;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public long getDistance() {
        return distance;
    }

    public String getSourceStationCode() {
        return sourceStationCode;
    }

    public String getSourceStationName() {
        return sourceStationName;
    }

    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }
}
