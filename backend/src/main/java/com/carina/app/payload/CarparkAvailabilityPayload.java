package com.carina.app.payload;

import com.carina.app.model.CarparkAvailabilityModel;

import java.util.ArrayList;
import java.util.List;

public class CarparkAvailabilityPayload {

    private List<CarparkAvailabilityModel> carparkAvailabilityModels;

    public CarparkAvailabilityPayload(List<CarparkAvailabilityModel> carparkAvailabilityModels) {
        this.carparkAvailabilityModels = carparkAvailabilityModels;
    }

    public List<CarparkAvailabilityModel> getCarparkAvailabilityModels() {
        return carparkAvailabilityModels;
    }
}
