package com.carina.app.controller;

import com.carina.app.model.CarparkAvailabilityMondayModel;
import com.carina.app.repository.CarparkAvailabilityMondayRepository;
import com.carina.app.service.CarparkAvailabilityService;
import com.carina.app.template.CarparkAvailabilityMondayTemplate;
import com.carina.app.utilities.DistanceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CarparkAvailabilityController {

    @Autowired
    private CarparkAvailabilityMondayRepository carparkAvailabilityMondayRepository;

    @Autowired
    private CarparkAvailabilityMondayTemplate carparkAvailabilityMondayTemplate;

    @GetMapping("/carpark-availability")
    public List<CarparkAvailabilityMondayModel> getCarparkAvailability() {
        return carparkAvailabilityMondayRepository.findAll();
    }

    @GetMapping("/carpark-availability/carpark-id-{idStr}")
    public List<CarparkAvailabilityMondayModel> getCarparkAvailabilityById(@PathVariable("idStr") String idStr) {
        return carparkAvailabilityMondayRepository.findByCarparkId(idStr);
    }

    @GetMapping("/carpark-availability/queries")
    public List<CarparkAvailabilityMondayModel> getCarparkAvailabilityByQueries(
            @RequestParam(defaultValue = ",") Set<String> day,
            @RequestParam(defaultValue = ",") Set<String> area,
            @RequestParam(defaultValue = ",") Set<String> development,
            @RequestParam(defaultValue = ",") Set<String> lotType
    ) {

        return carparkAvailabilityMondayTemplate.getCarparkAvailabilityByQueries(area, development, lotType);
    }

    @GetMapping("/carpark-availability/nearest/queries")
    public List<CarparkAvailabilityMondayModel> getCarparkAvailabilityNearestByQueries(
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
        ArrayList<CarparkAvailabilityMondayModel> listOfCarparkAvailabilityMondayModels =
                (ArrayList<CarparkAvailabilityMondayModel>) carparkAvailabilityMondayTemplate.getCarparkAvailabilityByQueries(area, development, lotType);

        return CarparkAvailabilityService.getNearestCarpark(latitudeSource, longitudeSource, radiusProximity, listOfCarparkAvailabilityMondayModels);
    }

}
