package com.radha.railwayrest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements Dao<T> {

    protected Connection connection;

    public AbstractDao() {
        DbConnection dataBaseConnection = new DbConnection();
        connection = dataBaseConnection.getConnection();
    }

    public int save(T t){

        /*Steps:
             i)Get the connection from the database
             ii)create a sql statement for insert
             iii)execute the sql statement
             1v)return the primary key

         */

        try {
            PreparedStatement saveStatement = getSaveStatement(t);
            if(saveStatement.executeUpdate() !=1 ){//check for how many rows is inserted into thw table
                throw new RuntimeException("can not save object ");
            }
            ResultSet resultSet = saveStatement.getGeneratedKeys();//returns the resultSet type which consist of primary key of the inserted row
            resultSet.next();// make the ResultSet object to point the next row,initially it point to row before the first row
            return resultSet.getInt(1);//getting the primary key which is in the first column
        } catch (SQLException e) {
            throw new RuntimeException("can not save object",e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public abstract PreparedStatement getSaveStatement(T t) throws SQLException;

    @Override
    public List<T> getAll() {
         /*Step 1:create a list of station called stations
          Step 2:Execute a sql query for select *
          step3;store it in the stations
          step4:return the stations

         */
        List<T> entities = new ArrayList<>();
        try {

            ResultSet resultSet = getGetAllStatement().executeQuery();
              /* if(!resultSet.next()){
                    return stations;
                }*/
            while (resultSet.next()) {
                entities.add(getEntity(resultSet));
            }
            return entities;

        } catch (SQLException e) {
            throw new RuntimeException("can not get all stations",e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public abstract PreparedStatement getGetAllStatement();

    public abstract T getEntity(ResultSet resultSet) throws SQLException;

    @Override
    public T get(int id){
        /*Steps:
        i)Declare 2 String variable called stationName and stationCode
        ii)execute a sql query for select
        iii)get the station name and store it in the stationName
        iv)get the station code and store it in the stationCode
        iv)return a new Station object with the argument of stationCode and stationName

         */

        try {


            getGetStatement().setInt(1, id);//set the id for the select query here 1 is the parameterIndex
            ResultSet resultSet = getGetStatement().executeQuery();
            if(!resultSet.next()){
                return null;
            }
            return getEntity(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException("can not get entity with id of " + id, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    public abstract PreparedStatement getGetStatement();

    public Connection getConnection() {
        return connection;
    }

}
