package com.carina.app.controller;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.service.CarparkAvailabilityService;
import com.carina.app.template.CarparkAvailabilityTemplate;
import com.carina.app.validation.DayOfWeekValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CarparkAvailabilityController {

    @Autowired
    private CarparkAvailabilityTemplate carparkAvailabilityTemplate;

    @GetMapping("/carpark-availability")
    public List<CarparkAvailabilityModel> getCarparkAvailability(@RequestParam() String day) {
        return carparkAvailabilityTemplate.findAll(DayOfWeekValidation.parseDayOfWeek(day));
    }

    @GetMapping("/carpark-availability/queries")
    public List<CarparkAvailabilityModel> getCarparkAvailabilityByQueries(
            @RequestParam(defaultValue = ",") Set<String> days,
            @RequestParam(defaultValue = ",") Set<String> area,
            @RequestParam(defaultValue = ",") Set<String> development,
            @RequestParam(defaultValue = ",") Set<String> lotType
    ) {
        ArrayList<CarparkAvailabilityModel> listOfCarparkAvailabilityModels = new ArrayList<>();
        for (String day: days) {
            listOfCarparkAvailabilityModels.addAll(carparkAvailabilityTemplate
                    .getCarparkAvailabilityByQueries(
                            DayOfWeekValidation.parseDayOfWeek(day), area, development, lotType
                    )
            );
        }
        return listOfCarparkAvailabilityModels;
    }

    @GetMapping("/carpark-availability/nearest")
    public List<CarparkAvailabilityModel> getCarparkAvailabilityNearest(
            @RequestParam() String day,
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String radius
    ) {
        double latitudeSource = Double.parseDouble(latitude);
        double longitudeSource = Double.parseDouble(longitude);
        double radiusProximity = Double.parseDouble(radius);
        ArrayList<CarparkAvailabilityModel> listOfCarparkAvailabilityModels =
                (ArrayList<CarparkAvailabilityModel>) carparkAvailabilityTemplate
                        .findAll(DayOfWeekValidation.parseDayOfWeek(day));

        return CarparkAvailabilityService.getNearestCarpark(
                latitudeSource, longitudeSource, radiusProximity,
                listOfCarparkAvailabilityModels
        );
    }

    @GetMapping("/carpark-availability/nearest/queries")
    public List<CarparkAvailabilityModel> getCarparkAvailabilityNearestByQueries(
            @RequestParam() String day,
            @RequestParam(defaultValue = ",") Set<String> area,
            @RequestParam(defaultValue = ",") Set<String> development,
            @RequestParam(defaultValue = ",") Set<String> lotType,
            @RequestParam String latitude,
            @RequestParam String longitude,
            @RequestParam String radius
    ) {
        double latitudeSource = Double.parseDouble(latitude);
        double longitudeSource = Double.parseDouble(longitude);
        double radiusProximity = Double.parseDouble(radius);
        ArrayList<CarparkAvailabilityModel> listOfCarparkAvailabilityMondayModels =
                (ArrayList<CarparkAvailabilityModel>) carparkAvailabilityTemplate
                        .getCarparkAvailabilityByQueries(
                                DayOfWeekValidation.parseDayOfWeek(day), area, development, lotType
                        );

        return CarparkAvailabilityService.getNearestCarpark(
                latitudeSource, longitudeSource, radiusProximity,
                listOfCarparkAvailabilityMondayModels
        );
    }

}
