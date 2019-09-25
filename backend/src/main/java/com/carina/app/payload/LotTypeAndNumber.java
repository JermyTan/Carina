package com.carina.app.payload;

public class LotTypeAndNumber {

    private String lotType;

    private String availableLots;

    public String getAvailableLots() {
        return availableLots;
    }

    public String getLotType() {
        return lotType;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public void setAvailableLots(String availableLots) {
        this.availableLots = availableLots;
    }
}
