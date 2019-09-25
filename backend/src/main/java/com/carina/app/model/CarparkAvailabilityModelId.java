package com.carina.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CarparkAvailabilityModelId implements Serializable {

    @Column(name = "carpark_id", nullable = false)
    private String carparkId;

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "development", nullable = false)
    private String development;

    @Column(name = "lot_type", nullable = false)
    private String lotType;

    @Column(name = "hour", nullable = false)
    private String hour;

    public CarparkAvailabilityModelId(String carparkId, String area, String development, String lotType, String hour) {
        this.carparkId = carparkId;
        this.area = area;
        this.development = development;
        this.lotType = lotType;
        this.hour = hour;
    }

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

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getLotType() {
        return this.lotType;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

}