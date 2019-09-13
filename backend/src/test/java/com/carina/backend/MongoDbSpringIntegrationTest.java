package com.carina.backend;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.data.mongodb.database=test"
})
class MongoDbSpringIntegrationTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    void setup() {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();
        // when
        mongoTemplate.save(objectToSave, "collection");
    }

    @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
    @Test
    void test(@Autowired MongoTemplate mongoTemplate) {
        // then
        List<DBObject> x  = mongoTemplate.findAll(DBObject.class, "collection");
        String str = x.toString();
        System.out.println(str);
    }
}