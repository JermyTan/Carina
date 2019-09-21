package com.carina.app.controller;

import com.carina.app.model.CarparkAvailabilityMondayModel;
import com.carina.app.repository.CarparkAvailabilityMondayRepository;
import com.carina.app.template.CarparkAvailabilityMondayTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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

        return carparkAvailabilityMondayTemplate.getCarparkAvailabilityByQueries(day, area, development, lotType);
    }

}
