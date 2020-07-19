package com.radha.railwayrest.dao;

import com.radha.railwayrest.Station;
import com.radha.railwayrest.Train;
import com.radha.railwayrest.TrainStop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StationDaoImpl extends AbstractDao<Station> {
    private PreparedStatement saveStatement;
    private PreparedStatement getStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getStationStatementByStationCode;


    public StationDaoImpl() {
        createSaveStatement();
        createGetStatement();
        createGetAllStatement();
        createGetStationByCodeStatement();
    }

    private void createSaveStatement() {
        try {
            saveStatement = connection.prepareStatement("insert into station(station_name,station_code)" +
                    "values(?,?)", Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create PreparedStatement to create station",e);
        }
    }


    private  void createGetStatement() {
        try {
            getStatement = connection.prepareStatement("select * from station where station_id=?");
        } catch (SQLException e) {
            throw new RuntimeException("Can not create PreparedStatement to get station", e);
        }
    }



    private void createGetAllStatement() {
        try {
            getAllStatement = connection.prepareStatement("select * from station");
        } catch (SQLException e) {
            throw new RuntimeException("Can not create PreparedStatement to get station", e);
        }
    }

    public Station getStation(String stationCode){
        /*Algorithm:
        Step 1:create a PreaparedStatement and execute the query for getting a station.
               Store the Query result into queryResult
        step 2:Process the queryResult to create the Station object as station and return it

         */
        try {
            getStationStatementByStationCode.setString(1, stationCode);
            ResultSet queryResult = getStationStatementByStationCode.executeQuery();
            return processQueryForGetStation(queryResult);

        } catch (SQLException e) {
            throw new RuntimeException("can not get station with station code of " +stationCode , e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    private  void  createGetStationByCodeStatement(){
        try {
            getStationStatementByStationCode = connection.prepareStatement("select station.station_id,station.station_name,station.station_code,\n" +
                    "       train.train_id,train.train_number,train.train_name,\n" +
                    "       train_stop.arrival_time,train_stop.departure_time,train_stop.sequence,\n" +
                    "       source_station.station_code as source_station_code,source_station.station_name as source_station_name,\n" +
                    "       destination_station.station_code as destination_station_code,destination_station.station_name as destination_station_name\n" +
                    "from station\n" +
                    "\t   inner join train_stop on \n" +
                    "       station.station_id = train_stop.station_id\n" +
                    "       inner join train on\n" +
                    "       train.train_id = train_stop.train_id\n" +
                    "       inner join station as source_station on\n" +
                    "       train.source_station_id = source_station.station_id\n" +
                    "       inner join station as destination_station on\n" +
                    "       train.destination_station_id = destination_station.station_id\n" +
                    "where station.station_code=?");
        } catch (SQLException e) {
            throw new RuntimeException("Can not create PreparedStatement to get station for the given code", e);
        }
    }
    private Station processQueryForGetStation(ResultSet resultSet) throws SQLException {
        /*Algorithm
        Step 1:If there is no row in the resultSet return null
        Step 2:create the station object from the first row
        Step 3:For each row in the resultSet do
                 i)create sourceStation and destinationStation
                 ii) create Train object
                 iii)create TrainStop object
                 iV)add the TrainStop object to Station object and Train object
        Step 4:return station object
         */
        if(!resultSet.next()){
            return null;

        }
        Station station = new Station(resultSet.getString("station_name"),resultSet.getString("station_code"));

        do{
            Train train = createTrain(resultSet);
            TrainStop trainStop = createTrainStop(resultSet, station, train);
            station.addTrainStop(trainStop);
            train.addTrainStop(trainStop);
        } while (resultSet.next());
        return station;

    }

    private TrainStop createTrainStop(ResultSet resultSet, Station station, Train train) throws SQLException {
       String arrivalTime = resultSet.getTime("arrival_time").toString();
       String departureTime = resultSet.getTime("departure_time").toString();
       int sequence = resultSet.getInt("sequence");
       return new TrainStop(arrivalTime, departureTime, train, sequence, station, 0);
    }

    private Train createTrain(ResultSet resultSet) throws SQLException {
        String trainName = resultSet.getString("train_name");
        String trainNumber = resultSet.getString("train_number");
        return new Train(trainName,
                trainNumber,
                createSourceStation(resultSet),
                createDestinationStation(resultSet));
    }

    private Station createDestinationStation(ResultSet resultSet) throws SQLException {
        String stationCode = resultSet.getString("destination_station_code");
        String stationName = resultSet.getString("destination_station_name");
        return new Station(stationName,stationCode);
    }

    private Station createSourceStation(ResultSet resultSet) throws SQLException {
        String stationCode = resultSet.getString("source_station_code");
        String stationName = resultSet.getString("source_station_name");
        return new Station(stationName,stationCode);
    }

    @Override
    public PreparedStatement getSaveStatement(Station station) throws SQLException {

        saveStatement.setString(1,station.getName());//1=>replacement position of question mark ,station.getName()=>actual station name
        saveStatement.setString(2,station.getCode());

        return saveStatement;
    }

    @Override
    public Station getEntity (ResultSet resultSet) throws SQLException {

            String stationName = resultSet.getString("station_name");
            String stationCode = resultSet.getString("station_code");
            System.out.println(resultSet.getInt("station_id"));
            return new Station(stationCode, stationName);


    }

    @Override
    public PreparedStatement getGetAllStatement() {
        return getAllStatement;
    }

    @Override
    public  PreparedStatement getGetStatement(){
        return getStatement;
    }
}
