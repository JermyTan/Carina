package com.carina.app.model;

import javax.persistence.*;

@MappedSuperclass
public class CarparkAvailabilityModel {

    @Id
    @Column(name = "carpark_id")
    private String carparkId;

    @Column(name = "area")
    private String area;

    @Column(name = "development")
    private String development;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "available_lots")
    private int availableLots;

    @Column(name = "lot_type")
    private String lotType;

    @Column(name = "agency")
    private String agency;

    @Column(name = "timestamp")
    private long timestamp;

    CarparkAvailabilityModel() {}

    public void setCarparkId(String carparkId) {
        this.carparkId = carparkId;
    }

    public String getCarparkId() {
        return this.carparkId;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }

    public String getDevelopment() {
        return this.development;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setAvailableLots(int availableLots) {
        this.availableLots = availableLots;
    }

    public int getAvailableLots() {
        return this.availableLots;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getLotType() {
        return this.lotType;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAgency() {
        return this.agency;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "carpark_availability";
    }
}