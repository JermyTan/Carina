package com.carina.app.controller;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.payload.CarparkAvailabilityPayload;
import com.carina.app.service.CarparkAvailabilityService;
import com.carina.app.template.CarparkAvailabilityTemplate;
import com.carina.app.validation.DayOfWeekValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CarparkAvailabilityController {

    @Autowired
    private CarparkAvailabilityTemplate carparkAvailabilityTemplate;

    /**
     * Returns response for the carparks availability at instance in time.
     * @param day day of the week.
     * @return list of carparks.
     */
    @GetMapping("/carpark-availability")
    public CarparkAvailabilityPayload getCarparkAvailability(@RequestParam() String day) {
        return new CarparkAvailabilityPayload(
                carparkAvailabilityTemplate.findAll(DayOfWeekValidation.parseDayOfWeek(day))
        );
    }

    /**
     * Returns response for carpark given the queries for days, areas, developments, and type of lots.
     * @param days list of days in the week.
     * @param areas list of areas.
     * @param developments list of developments.
     * @param lotTypes list of type of lots.
     * @return list of carpark availability.
     */
    @GetMapping("/carpark-availability/queries")
    public CarparkAvailabilityPayload getCarparkAvailabilityByQueries(
            @RequestParam(defaultValue = ",") Set<String> days,
            @RequestParam(defaultValue = ",") Set<String> areas,
            @RequestParam(defaultValue = ",") Set<String> developments,
            @RequestParam(defaultValue = ",") Set<String> lotTypes
    ) {
        ArrayList<CarparkAvailabilityModel> listOfCarparkAvailabilityModels = new ArrayList<>();
        for (String day: days) {
            listOfCarparkAvailabilityModels.addAll(carparkAvailabilityTemplate
                    .getCarparkAvailabilityByQueries(
                            DayOfWeekValidation.parseDayOfWeek(day), areas, developments, lotTypes
                    )
            );
        }
        return new CarparkAvailabilityPayload(listOfCarparkAvailabilityModels);
    }

    /**
     * Returns response for the nearest carparks availability at instance in time.
     * @param day day of week.
     * @param latitude latitude of source.
     * @param longitude longitude of source.
     * @param radius radius in metres.
     * @return list of carparks.
     */
    @GetMapping("/carpark-availability/nearest")
    public CarparkAvailabilityPayload getCarparkAvailabilityNearest(
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

        return new CarparkAvailabilityPayload(
                CarparkAvailabilityService.getNearestCarpark(
                latitudeSource, longitudeSource, radiusProximity,
                listOfCarparkAvailabilityModels
        ));
    }

    /**
     * Returns response for queries of nearest carpark availability within proximity at given instance in time.
     * @param day day of week from request.
     * @param areas list of area from request.
     * @param developments list of development from request.
     * @param lotTypes list of lot types from request.
     * @param latitude latitude of source.
     * @param longitude longitude of source.
     * @param radius radius in metres.
     * @return list of carpark.
     */
    @GetMapping("/carpark-availability/nearest/queries")
    public CarparkAvailabilityPayload getCarparkAvailabilityNearestByQueries(
            @RequestParam() String day,
            @RequestParam(defaultValue = ",") Set<String> areas,
            @RequestParam(defaultValue = ",") Set<String> developments,
            @RequestParam(defaultValue = ",") Set<String> lotTypes,
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
                                DayOfWeekValidation.parseDayOfWeek(day), areas, developments, lotTypes
                        );

        return new CarparkAvailabilityPayload(
                CarparkAvailabilityService.getNearestCarpark(
                latitudeSource, longitudeSource, radiusProximity,
                listOfCarparkAvailabilityMondayModels
        ));
    }

}
