package com.carina.app.utility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DistanceCalculationTest {

    @Test
    void testGetDistance() {
        int distance = (int) DistanceCalculationUtility.getDistance(
                0.9115798064685449, 0.36673257399704784,
                0.9146637753315507, 0.2953999097985129
        );
        assertEquals(7939, distance);
    }
}
