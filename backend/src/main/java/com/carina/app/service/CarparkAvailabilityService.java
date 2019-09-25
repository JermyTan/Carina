package com.carina.app.service;

import com.carina.app.constant.CarparkAvailabilitySqlQueryConstant;
import com.carina.app.constant.DayOfWeekConstant;
import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.template.CarparkAvailabilityTemplate;
import com.carina.app.utility.DistanceCalculationUtility;
import org.springframework.beans.factory.annotation.Autowired;

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
            String latitudeDestination = carparkAvailabilityModel.getLatitude();
            String longitudeDestination = carparkAvailabilityModel.getLongitude();
            if (DistanceCalculationUtility.withinPromixity(
                    latitudeSource, longitudeSource,
                    Double.parseDouble(latitudeDestination), Double.parseDouble(longitudeDestination),
                    radiusProximity
            )) {
                listOfNearestCarparkAvailabilityMondayModels.add(carparkAvailabilityModel);
            }

        }
        return listOfNearestCarparkAvailabilityMondayModels;
    }

}
