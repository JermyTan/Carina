package com.carina.backend.controllers;


import com.carina.backend.repositories.CarparkAvailabilityRepository;
import com.carina.backend.services.CarparkAvailability;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carpark-availability")
public class CarparkAvailabilityController {
    @Autowired
    private CarparkAvailabilityRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<CarparkAvailability> getAllCarParks() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CarparkAvailability getCarParkById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyCarParkById(@PathVariable("id") ObjectId id, @Valid @RequestBody CarparkAvailability carpark) {
        carpark.set_id(id);
        repository.save(carpark);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CarparkAvailability createPet(@Valid @RequestBody CarparkAvailability carpark) {
        carpark.set_id(ObjectId.get());
        repository.save(carpark);
        return carpark;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCarPark(@PathVariable ObjectId id) {
        repository.delete(repository.findBy_id(id));
    }
}