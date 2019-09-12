package com.carina.backend.templates;

import com.carina.backend.services.CarparkAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// http://appsdeveloperblog.com/spring-boot-and-mongotemplate-tutorial-with-mongodb/
@Repository
public class CarparkAvailabilityTemplate {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CarparkAvailabilityTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<CarparkAvailability> findByArea(String area) {
        Query query = new Query();
        query.addCriteria(Criteria.where("Area").is(area));
        return mongoTemplate.find(query, CarparkAvailability.class);
    }

}
