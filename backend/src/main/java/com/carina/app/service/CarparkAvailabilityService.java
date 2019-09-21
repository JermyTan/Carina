package com.carina.app.service;

import com.carina.app.model.CarparkAvailabilityMondayModel;
import com.carina.app.utilities.DistanceCalculationUtilities;

import java.util.ArrayList;

public class CarparkAvailabilityService {

    public static ArrayList<CarparkAvailabilityMondayModel> getNearestCarpark(
            double latitudeSource, double longitudeSource, double radiusProximity, ArrayList<CarparkAvailabilityMondayModel> listOfCarparkAvailabilityMondayModels
    ) {
        ArrayList<CarparkAvailabilityMondayModel> listOfNearestCarparkAvailabilityMondayModels = new ArrayList<>();
        for (CarparkAvailabilityMondayModel carparkAvailabilityMondayModel : listOfCarparkAvailabilityMondayModels) {
            double latitudeDestination = carparkAvailabilityMondayModel.getLatitude();
            double longitudeDestination = carparkAvailabilityMondayModel.getLongitude();
            if (DistanceCalculationUtilities.withinPromixity(latitudeSource, longitudeSource, latitudeDestination, longitudeDestination, radiusProximity )) {
                listOfNearestCarparkAvailabilityMondayModels.add(carparkAvailabilityMondayModel);
            }

        }
        return listOfNearestCarparkAvailabilityMondayModels;
    }
}
