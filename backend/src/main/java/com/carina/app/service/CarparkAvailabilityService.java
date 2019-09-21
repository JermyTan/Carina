package com.carina.app.service;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.utilities.DistanceCalculationUtilities;

import java.util.ArrayList;

public class CarparkAvailabilityService {

    public static <T extends CarparkAvailabilityModel> ArrayList<T> getNearestCarpark(
            double latitudeSource, double longitudeSource, double radiusProximity, ArrayList<T> listOfCarparkAvailabilityMondayModels
    ) {
        ArrayList<T> listOfNearestCarparkAvailabilityMondayModels = new ArrayList<>();
        for (T carparkAvailabilityMondayModel : listOfCarparkAvailabilityMondayModels) {
            double latitudeDestination = carparkAvailabilityMondayModel.getLatitude();
            double longitudeDestination = carparkAvailabilityMondayModel.getLongitude();
            if (DistanceCalculationUtilities.withinPromixity(latitudeSource, longitudeSource, latitudeDestination, longitudeDestination, radiusProximity )) {
                listOfNearestCarparkAvailabilityMondayModels.add(carparkAvailabilityMondayModel);
            }

        }
        return listOfNearestCarparkAvailabilityMondayModels;
    }
}
