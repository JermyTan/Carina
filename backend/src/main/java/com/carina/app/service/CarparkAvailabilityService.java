package com.carina.app.service;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.utility.DistanceCalculationUtility;

import java.util.ArrayList;

public class CarparkAvailabilityService {

    /**
     * Returns list of carparks nearest to current location at instance in time.
     * @param latitudeSource latitude of source.
     * @param longitudeSource longitude of source.
     * @param radiusProximity radius in metres.
     * @param listOfCarparkAvailabilityModels list of carparks at the instance in time.
     * @return list of carparks within proximity.
     */
    public static ArrayList<CarparkAvailabilityModel> getNearestCarpark(
            double latitudeSource, double longitudeSource, double radiusProximity,
            ArrayList<CarparkAvailabilityModel> listOfCarparkAvailabilityModels
    ) {
        ArrayList<CarparkAvailabilityModel> listOfNearestCarparkAvailabilityMondayModels = new ArrayList<>();
        for (CarparkAvailabilityModel carparkAvailabilityModel : listOfCarparkAvailabilityModels) {
            double latitudeDestination = carparkAvailabilityModel.getLatitude();
            double longitudeDestination = carparkAvailabilityModel.getLongitude();
            if (DistanceCalculationUtility.withinPromixity(
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
