package com.carina.backend.controllers;

import com.carina.backend.repositories.CarparkAvailabilityRepository;
import com.carina.backend.services.CarparkAvailability;
import com.carina.backend.validations.BsonIDValidator;
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
    public CarparkAvailability getCarParkById(@PathVariable("id") String idStr) {
        BsonIDValidator validator = new BsonIDValidator(idStr);
        return repository.findBy_id(validator.getObjectId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyCarParkById(@PathVariable("id") String idStr, @Valid @RequestBody CarparkAvailability carpark) {
        BsonIDValidator validator = new BsonIDValidator(idStr);
        carpark.set_id(validator.getObjectId());
        repository.save(carpark);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CarparkAvailability createPet(@Valid @RequestBody CarparkAvailability carpark) {
        carpark.set_id(ObjectId.get());
        repository.save(carpark);
        return carpark;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCarPark(@PathVariable String idStr) {
        BsonIDValidator validator = new BsonIDValidator(idStr);
        repository.delete(repository.findBy_id(validator.getObjectId()));
    }
}