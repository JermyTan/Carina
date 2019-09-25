package com.carina.app.payload;

import com.google.gson.annotations.SerializedName;

public class LtaDataMallValuePayload {

    @SerializedName("CarParkID")
    private String carparkId;

    @SerializedName("Area")
    private String area;

    @SerializedName("Development")
    private String development;

    @SerializedName("Location")
    private String location;

    @SerializedName("AvailableLots")
    private String availableLots;

    @SerializedName("LotType")
    private String lotType;

    @SerializedName("Agency")
    private String agency;

    public String getCarparkId() {
        return carparkId;
    }

    public void setCarparkId(String carpark_id) {
        this.carparkId = carpark_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDevelopment() {
        return development;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }

    public String getLotType() {
        return lotType;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAvailableLots() {
        return availableLots;
    }

    public void setAvailableLots(String availableLots) {
        this.availableLots = availableLots;
    }
}