package com.radha.railwayrest.dao;

import com.radha.railwayrest.Station;
import com.radha.railwayrest.Train;
import com.radha.railwayrest.TrainStop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TrainStopDaoImpl extends AbstractDao<TrainStop> {

    private PreparedStatement saveStatement;
    private PreparedStatement getStatement;
    private PreparedStatement getAllStatement;

    public TrainStopDaoImpl(){
        createSaveStatement();
        createGetStatement();
        createGetAllStatement();


    }

    private void createSaveStatement() {
        try {
            saveStatement = connection.prepareStatement("insert into train_stop" +
                    "(train_id,station_id,sequence,arrival_time,departure_time)" +
                    "values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        }
        catch (SQLException e){
            throw new RuntimeException("can not save trainStop",e);
        }
    }

    @Override
    public PreparedStatement getSaveStatement(TrainStop trainStop) throws SQLException {
        saveStatement.setInt(1,trainStop.getTrain().getId());
        saveStatement.setInt(2,trainStop.getStation().getId());
        saveStatement.setInt(3,trainStop.getSequence());
        saveStatement.setString(4,trainStop.getArrivalTime());
        saveStatement.setString(5,trainStop.getDepartureTime());

        return saveStatement;
    }

    private void createGetStatement(){
        try {
            getStatement = connection.prepareStatement("select station.station_id,station.station_name,station.station_code,\n" +
                    "       train.train_id,train.train_number,train.train_name,\n" +
                    "       train_stop.arrival_time,train_stop.departure_time,train_stop.sequence,train_stop.train_stop_id,\n" +
                    "       source_station.station_code as source_station_code,source_station.station_name as source_station_name,\n" +
                    "       destination_station.station_code as destination_station_code,destination_station.station_name as destination_station_name\n" +
                    "from train_stop\n" +
                    "\t   inner join station on \n" +
                    "       station.station_id = train_stop.station_id\n" +
                    "       inner join train on\n" +
                    "       train.train_id = train_stop.train_id\n" +
                    "       inner join station as source_station on\n" +
                    "       train.source_station_id = source_station.station_id\n" +
                    "       inner join station as destination_station on\n" +
                    "       train.destination_station_id = destination_station.station_id\n" +
                    "where train_stop.train_stop_id=?\n" +
                    "order by train.train_id;\n");
        }
        catch (SQLException e){
            throw new RuntimeException("Can not get train_stop ", e);
        }


    }

    @Override
    public PreparedStatement getGetStatement(){
        return getStatement;
    }

    private void createGetAllStatement(){
        try{
            getAllStatement = connection.prepareStatement("select station.station_id,station.station_name,station.station_code,\n" +
                    "       train.train_id,train.train_number,train.train_name,\n" +
                    "       train_stop.arrival_time,train_stop.departure_time,train_stop.sequence,\n" +
                    "       source_station.station_code as source_station_code,source_station.station_name as source_station_name,\n" +
                    "       destination_station.station_code as destination_station_code,destination_station.station_name as destination_station_name\n" +
                    "from train_stop\n" +
                    "\t   inner join station on \n" +
                    "       station.station_id = train_stop.station_id\n" +
                    "       inner join train on\n" +
                    "       train.train_id = train_stop.train_id\n" +
                    "       inner join station as source_station on\n" +
                    "       train.source_station_id = source_station.station_id\n" +
                    "       inner join station as destination_station on\n" +
                    "       train.destination_station_id = destination_station.station_id\n" +
                    "order by train.train_id;\n");
        }catch (SQLException e){
            throw new RuntimeException("Can not get train_stops ", e);
        }
    }

    @Override
    public PreparedStatement getGetAllStatement(){
        return getAllStatement;
    }

    @Override
    public TrainStop getEntity(ResultSet resultSet) throws SQLException {
        Station station = createStation(resultSet);
        Train train = createTrain(resultSet);
        return createTrainStop(resultSet,station,train);

    }
    private TrainStop createTrainStop(ResultSet resultSet, Station station, Train train) throws SQLException {
        Integer trainStopId = resultSet.getInt("train_stop_id");
        String arrivalTime = resultSet.getTime("arrival_time").toString();
        String departureTime = resultSet.getTime("departure_time").toString();
        int sequence = resultSet.getInt("sequence");
        return new TrainStop(trainStopId,arrivalTime, departureTime, train, sequence, station, 0);
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

    private Station createStation(ResultSet resultSet) throws SQLException{
        String stationCode = resultSet.getString("station_code");
        String stationName = resultSet.getString("station_name");
        return new Station(stationName,stationCode);
    }
}
