package com.carina.app.payload;

import java.util.Map;
import java.util.Set;

public class LtaDataMallFinalPayload {

    private String carparkId;

    private String area;

    private String development;

    private String latitude;

    private String longitude;

    private Map<String, String> lots;

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

    public Map<String, String> getLots() {
        return lots;
    }

    public void setLots(Map<String, String> lots) {
        this.lots = lots;
    }

    public void addLots(String a, String b) {
        this.lots.put(a, b);
    }

    @Override
    public String toString() {
        return carparkId + "-" + area + "-" + development;
    }
}