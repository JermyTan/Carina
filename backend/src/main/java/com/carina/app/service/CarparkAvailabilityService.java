package com.carina.app.service;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.utilities.DistanceCalculationUtilities;

import java.util.ArrayList;

public class CarparkAvailabilityService {

    public static ArrayList<CarparkAvailabilityModel> getNearestCarpark(
            double latitudeSource, double longitudeSource, double radiusProximity,
            ArrayList<CarparkAvailabilityModel> listOfCarparkAvailabilityMondayModels
    ) {
        ArrayList<CarparkAvailabilityModel> listOfNearestCarparkAvailabilityMondayModels = new ArrayList<>();
        for (CarparkAvailabilityModel carparkAvailabilityModel : listOfCarparkAvailabilityMondayModels) {
            double latitudeDestination = carparkAvailabilityModel.getLatitude();
            double longitudeDestination = carparkAvailabilityModel.getLongitude();
            if (DistanceCalculationUtilities.withinPromixity(
                    latitudeSource, longitudeSource,
                    latitudeDestination, longitudeDestination,
                    radiusProximity
            )) {
                listOfNearestCarparkAvailabilityMondayModels.add(carparkAvailabilityModel);
            }

        }
        return listOfNearestCarparkAvailabilityMondayModels;
    }
}
