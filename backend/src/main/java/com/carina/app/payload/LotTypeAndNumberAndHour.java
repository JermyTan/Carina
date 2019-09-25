package com.carina.app.payload;

public class LotTypeAndNumberAndHour {

    private String lotType;

    private String availableLots;

    private String hour;

    private String timeLabel;

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

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

    public String getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel = timeLabel;
    }

    @Override
    public int hashCode(){
        int hashcode = 0;
        hashcode += lotType.hashCode();
        hashcode += hour.hashCode();
        return hashcode;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof LotTypeAndNumberAndHour) {
            LotTypeAndNumberAndHour pp = (LotTypeAndNumberAndHour) obj;
            return (pp.getLotType().equals(this.getLotType()) && pp.getHour().equals(this.getHour()));
        } else {
            return false;
        }
    }

}
