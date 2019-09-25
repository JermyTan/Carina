package com.carina.app.payload;

public class LtaDataMallProcessedPayloadDistance {

    private String carparkId;

    private String area;

    private String development;

    private String latitude;

    private String longitude;

    private String lotType;

    private String hour;

    private String availableLots;

    private String distFromSrc;

    public String getDistFromSrc() {
        return distFromSrc;
    }

    public void setDistFromSrc(String distFromSrc) {
        this.distFromSrc = distFromSrc;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

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

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getAvailableLots() {
        return availableLots;
    }

    public void setAvailableLots(String availableLots) {
        this.availableLots = availableLots;
    }

    @Override
    public String toString() {
        return carparkId + "-" + area + "-" + development;
    }
}