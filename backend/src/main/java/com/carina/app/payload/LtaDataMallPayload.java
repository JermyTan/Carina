package com.carina.app.payload;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LtaDataMallPayload {

    @SerializedName("odata.metadata")
    private String odata;

    @SerializedName("value")
    private ArrayList<LtaDataMallValuePayload> value;

    public ArrayList<LtaDataMallValuePayload> getValue() {
        return value;
    }

    public void setOdata(String odata) {
        this.odata = odata;
    }

    public String getOdata() {
        return odata;
    }

    public void setValue(ArrayList<LtaDataMallValuePayload> value) {
        this.value = value;
    }
}