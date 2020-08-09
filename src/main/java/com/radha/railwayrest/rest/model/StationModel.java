
package com.radha.railwayrest.rest.model;

public class StationModel {
    private String name;
    private String code;
    private double latitude;
    private double longitude;



    public StationModel(String name, String code, double latitude, double longitude) {
        this.name = name;
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public boolean equals(Object other) {
        if (other instanceof StationModel) {
            StationModel otherStation = (StationModel) other;
            if (this.getCode().equals(otherStation.getCode())) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    public String toString(){
        return "StationName:"+this.name+" StationCode:"+this.code;
    }
}