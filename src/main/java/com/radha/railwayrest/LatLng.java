package com.radha.railwayrest;

public class LatLng {
    private double latitude;
    private double longitude;


    public LatLng(double latitude,double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public boolean equals(Object other) {
        if (other instanceof LatLng) {
            LatLng otherLatlng = (LatLng) other;
            if (this.getLatitude() == otherLatlng.getLatitude()&&this.getLongitude() == otherLatlng.getLongitude()) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public String toString() {
        return " Latitude:"+this.latitude+" Longitude:"+this.longitude;
    }


}

