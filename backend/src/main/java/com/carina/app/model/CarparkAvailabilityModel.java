package com.carina.app.model;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;


@MappedSuperclass
public class CarparkAvailabilityModel {

    @EmbeddedId
    private CarparkAvailabilityModelId carparkAvailabilityId;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "available_lots")
    private String availableLots;

    @Column(name = "agency")
    private String agency;

    @Column(name = "timestamp")
    private String timestamp;

    public CarparkAvailabilityModel() {
    }

    public CarparkAvailabilityModelId getCarparkAvailabilityId() {
        return carparkAvailabilityId;
    }

    public void setCarparkAvailabilityId(CarparkAvailabilityModelId carparkAvailabilityId) {
        this.carparkAvailabilityId = carparkAvailabilityId;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setAvailableLots(String availableLots) {
        this.availableLots = availableLots;
    }

    public String getAvailableLots() {
        return this.availableLots;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAgency() {
        return this.agency;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

}