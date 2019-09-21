package com.carina.app.service;

import com.carina.app.model.CarparkAvailabilityMondayModel;
import com.carina.app.repository.CarparkAvailabilityMondayRepository;
import com.carina.app.template.CarparkAvailabilityMondayTemplate;
import com.carina.app.utilities.DistanceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CarparkAvailabilityService {

    @Autowired
    private CarparkAvailabilityMondayRepository carparkAvailabilityMondayRepository;

    @Autowired
    private CarparkAvailabilityMondayTemplate carparkAvailabilityMondayTemplate;

    public static ArrayList<CarparkAvailabilityMondayModel> getNearestCarpark(
            double latitudeSource, double longitudeSource, double radiusProximity, ArrayList<CarparkAvailabilityMondayModel> listOfCarparkAvailabilityMondayModels
    ) {
        ArrayList<CarparkAvailabilityMondayModel> listOfNearestCarparkAvailabilityMondayModels = new ArrayList<>();
        for (CarparkAvailabilityMondayModel carparkAvailabilityMondayModel : listOfCarparkAvailabilityMondayModels) {
            double latitudeDestination = carparkAvailabilityMondayModel.getLatitude();
            double longitudeDestination = carparkAvailabilityMondayModel.getLongitude();
            if (DistanceCalculationService.withinPromixity(latitudeSource, longitudeSource, latitudeDestination, longitudeDestination, radiusProximity )) {
                listOfNearestCarparkAvailabilityMondayModels.add(carparkAvailabilityMondayModel);
            }

        }
        return listOfNearestCarparkAvailabilityMondayModels;
    }
}
