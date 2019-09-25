package com.carina.app.controller;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.payload.*;
import com.carina.app.service.CarparkAvailabilityService;
import com.carina.app.service.LtaDataMallGetRequestService;
import com.carina.app.template.CarparkAvailabilityTemplate;
import com.carina.app.utility.DistanceCalculationUtility;
import com.carina.app.validation.DayOfWeekValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/public")
public class CarparkAvailabilityController {

    @Autowired
    private CarparkAvailabilityTemplate carparkAvailabilityTemplate;

    @GetMapping("/carpark-availability/latest")
    public Collection<LtaDataMallFinalPayload> getLatestCarparks() throws IOException {
        ArrayList<LtaDataMallValuePayload> data = LtaDataMallGetRequestService.getAllLtaDataMallValue();
        ArrayList<LtaDataMallProcessedPayload> processedData = new ArrayList<>();
        for (LtaDataMallValuePayload i: data) {
            LtaDataMallProcessedPayload p = new LtaDataMallProcessedPayload();
            p.setCarparkId(i.getCarparkId());
            p.setArea(i.getArea());
            p.setDevelopment(i.getDevelopment());
            p.setLotType(i.getLotType());
            p.setAvailableLots(i.getAvailableLots());
            String[] loc = i.getLocation().split("\\s+");
            if (loc.length > 1) {
                p.setLatitude(loc[0]);
                p.setLongitude(loc[1]);
                processedData.add(p);
            }
        }

        Map<String, LtaDataMallFinalPayload> payload = new HashMap<>();
        for (LtaDataMallProcessedPayload p: processedData) {
            if (payload.containsKey(p.toString())) {
                payload.get(p.toString()).addLots(p.getLotType(), p.getAvailableLots());
            } else {
                LtaDataMallFinalPayload a = new LtaDataMallFinalPayload();
                a.setCarparkId(p.getCarparkId());
                a.setArea(p.getArea());
                a.setDevelopment(p.getDevelopment());
                a.setLatitude(p.getLatitude());
                a.setLongitude(p.getLongitude());
                a.setLots(new HashMap<>());
                payload.put(p.toString(), a);
                payload.get(p.toString()).addLots(p.getLotType(), p.getAvailableLots());
            }
        }
        return payload.values();
    }


    @GetMapping("/carpark-availability/statistics")
    public Map<String, Set<LotTypeAndNumberAndHour>> getCarparkStatistics(
        @RequestParam String carpark_id,
        @RequestParam(defaultValue = "") Set<String> lotTypes
    ) {
        String [] days = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        Map<String, Set<LotTypeAndNumberAndHour>> payload = new HashMap<>();

        for (String day: days) {
            ArrayList<LtaDataMallProcessedPayload> processedData = new ArrayList<>();

            CarparkAvailabilityPayload payloadByDay = new CarparkAvailabilityPayload(
                carparkAvailabilityTemplate.getCarparkInfo(DayOfWeekValidation.parseDayOfWeek(day), carpark_id));

            ArrayList<CarparkAvailabilityModel> models = (ArrayList<CarparkAvailabilityModel>) payloadByDay.getCarparkAvailabilityModels();
            for (CarparkAvailabilityModel m: models) {
                LtaDataMallProcessedPayload p = new LtaDataMallProcessedPayload();
                p.setCarparkId(carpark_id);
                p.setArea(m.getCarparkAvailabilityId().getArea());
                p.setDevelopment(m.getCarparkAvailabilityId().getDevelopment());
                p.setLatitude(m.getLatitude());
                p.setLongitude(m.getLongitude());
                p.setLotType(m.getCarparkAvailabilityId().getLotType());
                p.setAvailableLots(m.getAvailableLots());
                p.setHour(m.getCarparkAvailabilityId().getHour());
                processedData.add(p);
            }

            HashSet<LotTypeAndNumberAndHour> set = new HashSet<>();
            for (LtaDataMallProcessedPayload p: processedData) {
                LotTypeAndNumberAndHour lotTypeAndNumberAndHour = new LotTypeAndNumberAndHour();
                lotTypeAndNumberAndHour.setHour(p.getHour());
                int h = Integer.parseInt(p.getHour());
                if (h < 12) {
                    lotTypeAndNumberAndHour.setTimeLabel(h+"am");
                } else {
                    lotTypeAndNumberAndHour.setTimeLabel(h+"pm");
                }
                lotTypeAndNumberAndHour.setAvailableLots(p.getAvailableLots());
                lotTypeAndNumberAndHour.setLotType(p.getLotType());
                if (lotTypes.contains(p.getLotType())) {
                    set.add(lotTypeAndNumberAndHour);
                }
            }
            payload.put(day, set);
        }

        return payload;
    }

    /**
     * Returns response for queries of nearest carpark availability within proximity at given instance in time.
     * @param lotTypes list of lot types from request.
     * @param latitude latitude of source.
     * @param longitude longitude of source.
     * @param radius radius in metres.
     * @return list of carpark.
     */
    @GetMapping("/carpark-availability/nearest/queries")
    public Collection<LtaDataMallFinalPayloadDistance>  getCarparkAvailabilityNearestByQueries(
        @RequestParam String latitude,
        @RequestParam String longitude,
        @RequestParam String radius,
        @RequestParam(defaultValue = "") Set<String> lotTypes
    ) throws IOException {
        double latitudeSource = Double.parseDouble(latitude);
        double longitudeSource = Double.parseDouble(longitude);
        double radiusProximity = Double.parseDouble(radius);
        ArrayList<LtaDataMallValuePayload> data = LtaDataMallGetRequestService.getAllLtaDataMallValue();
        ArrayList<LtaDataMallProcessedPayloadDistance> processedData = new ArrayList<>();
        for (LtaDataMallValuePayload i: data) {
            LtaDataMallProcessedPayloadDistance p = new LtaDataMallProcessedPayloadDistance();
            p.setCarparkId(i.getCarparkId());
            p.setArea(i.getArea());
            p.setDevelopment(i.getDevelopment());
            p.setLotType(i.getLotType());
            p.setAvailableLots(i.getAvailableLots());
            String[] loc = i.getLocation().split("\\s+");
            if (loc.length > 1) {
                p.setLatitude(loc[0]);
                p.setLongitude(loc[1]);

                if (DistanceCalculationUtility.withinPromixity(
                    latitudeSource, longitudeSource, Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude()), radiusProximity
                )) {
                    p.setDistFromSrc(String.valueOf(DistanceCalculationUtility.getDistance(
                        latitudeSource, longitudeSource, Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude())
                    )));
                    processedData.add(p);
                }
            }
        }

        Map<String, LtaDataMallFinalPayloadDistance> payload = new HashMap<>();
        for (LtaDataMallProcessedPayloadDistance p: processedData) {
            if (payload.containsKey(p.toString())) {
                payload.get(p.toString()).addLots(p.getLotType(), p.getAvailableLots());
            } else {
                LtaDataMallFinalPayloadDistance a = new LtaDataMallFinalPayloadDistance();
                a.setCarparkId(p.getCarparkId());
                a.setArea(p.getArea());
                a.setDevelopment(p.getDevelopment());
                a.setLatitude(p.getLatitude());
                a.setLongitude(p.getLongitude());
                a.setLots(new HashMap<>());
                a.setDistFromSrc(p.getDistFromSrc());
                payload.put(p.toString(), a);
                payload.get(p.toString()).addLots(p.getLotType(), p.getAvailableLots());
            }
        }
        return payload.values();
    }

    @GetMapping("/carpark-availability/carpark-id")
    public Map<String, LtaDataMallFinalPayload>  getCarparkAvailabilityByCarparkId(
        @RequestParam Set<String> carparkIds,
        @RequestParam(defaultValue = "") Set<String> lotTypes
    ) throws IOException {
        ArrayList<LtaDataMallValuePayload> data = LtaDataMallGetRequestService.getAllLtaDataMallValue();
        ArrayList<LtaDataMallProcessedPayload> processedData = new ArrayList<>();
        for (LtaDataMallValuePayload i: data) {
            LtaDataMallProcessedPayload p = new LtaDataMallProcessedPayload();
            p.setCarparkId(i.getCarparkId());
            p.setArea(i.getArea());
            p.setDevelopment(i.getDevelopment());
            p.setLotType(i.getLotType());
            p.setAvailableLots(i.getAvailableLots());
            String[] loc = i.getLocation().split("\\s+");
            if (loc.length > 1) {
                p.setLatitude(loc[0]);
                p.setLongitude(loc[1]);
                processedData.add(p);
            }
        }

        Map<String, LtaDataMallFinalPayload> payload = new HashMap<>();
        for (LtaDataMallProcessedPayload p: processedData) {
            if (payload.containsKey(p.toString())) {
                payload.get(p.toString()).addLots(p.getLotType(), p.getAvailableLots());
            } else {
                LtaDataMallFinalPayload a = new LtaDataMallFinalPayload();
                a.setCarparkId(p.getCarparkId());
                a.setArea(p.getArea());
                a.setDevelopment(p.getDevelopment());
                a.setLatitude(p.getLatitude());
                a.setLongitude(p.getLongitude());
                a.setLots(new HashMap<>());
                payload.put(p.toString(), a);
                payload.get(p.toString()).addLots(p.getLotType(), p.getAvailableLots());
            }
        }
        Collection<LtaDataMallFinalPayload> ltaDataMallFinalPayloadCollection = payload.values();

        Map<String, LtaDataMallFinalPayload> payloadMap = new HashMap<>();

        for (LtaDataMallFinalPayload ltaDataMallFinalPayload: ltaDataMallFinalPayloadCollection) {
            if (carparkIds.contains(ltaDataMallFinalPayload.getCarparkId())) {
                payloadMap.put(ltaDataMallFinalPayload.getCarparkId(), ltaDataMallFinalPayload);
            }
        }
        return payloadMap;
    }
}