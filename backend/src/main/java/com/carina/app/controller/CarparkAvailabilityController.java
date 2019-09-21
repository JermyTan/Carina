package com.carina.app.controller;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.model.CarparkAvailabilityMondayModel;
import com.carina.app.service.CarparkAvailabilityService;
import com.carina.app.template.CarparkAvailabilityTemplate;
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
        return carparkAvailabilityTemplate.findAll(day);
    }

    @GetMapping("/carpark-availability/queries")
    public List<CarparkAvailabilityModel> getCarparkAvailabilityByQueries(
            @RequestParam(defaultValue = ",") Set<String> day,
            @RequestParam(defaultValue = ",") Set<String> area,
            @RequestParam(defaultValue = ",") Set<String> development,
            @RequestParam(defaultValue = ",") Set<String> lotType
    ) {

        return carparkAvailabilityTemplate.getCarparkAvailabilityByQueries(area, development, lotType);
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
                (ArrayList<CarparkAvailabilityModel>) carparkAvailabilityTemplate.findAll(day);

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
                        .getCarparkAvailabilityByQueries(area, development, lotType);

        return CarparkAvailabilityService.getNearestCarpark(
                latitudeSource, longitudeSource, radiusProximity,
                listOfCarparkAvailabilityMondayModels
        );
    }

}
