package com.carina.backend.repositories;

import com.carina.backend.services.CarparkAvailability;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarparkAvailabilityRepository extends MongoRepository<CarparkAvailability, String> {
    CarparkAvailability findBy_id(ObjectId _id);
}