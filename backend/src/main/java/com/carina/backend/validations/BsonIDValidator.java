package com.carina.backend.validations;

import com.carina.backend.exceptions.IDNotBosonID;
import com.carina.backend.repositories.CarparkAvailabilityRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class BsonIDValidator {

    @Autowired
    private CarparkAvailabilityRepository repository;
    private String idStr;
    private ObjectId id;

    public BsonIDValidator(String idStr) {
        this.idStr = idStr;
        this.validate();
    }

    private void validate() {
        try {
            ObjectId id = new ObjectId(idStr);
            this.id = id;
        } catch (Exception e) {
            throw new IDNotBosonID("conversion error of id " + idStr);
        }
    }

    public ObjectId getObjectId() {
        return id;
    }

}
