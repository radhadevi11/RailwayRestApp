
package com.radha.railwayrest.db.entity;


import javax.persistence.*;

@Entity
@Table(name = "station")
public class StationEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Integer id;
    @Column(name = "station_name")
    private String name;
    @Column(name = "station_code")
    private String code;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;


    public StationEntity() {
    }



    public StationEntity(String name, String code, double latitude, double longitude) {
        this(null, name, code, latitude, longitude);
    }

    public StationEntity(Integer id, String name, String code, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StationEntity(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public boolean equals(Object other) {
        if (other instanceof StationEntity) {
            StationEntity otherStation = (StationEntity) other;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}