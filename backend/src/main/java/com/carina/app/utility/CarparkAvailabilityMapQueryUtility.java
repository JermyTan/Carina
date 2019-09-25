package com.carina.app.utility;

import com.carina.app.constant.CarparkAvailabilitySqlQueryConstant;
import com.carina.app.constant.DayOfWeekConstant;

import java.util.AbstractMap;
import java.util.Map;

public class CarparkAvailabilityMapQueryUtility {

    private static Map<DayOfWeekConstant, String> mapFindAllQueries = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.MONDAY,
                    CarparkAvailabilitySqlQueryConstant.findAllMonday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.TUESDAY,
                    CarparkAvailabilitySqlQueryConstant.findAllTuesday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.WEDNESDAY,
                    CarparkAvailabilitySqlQueryConstant.findAllWednesday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.THURSDAY,
                    CarparkAvailabilitySqlQueryConstant.findAllThursday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.FRIDAY,
                    CarparkAvailabilitySqlQueryConstant.findAllFriday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SATURDAY,
                    CarparkAvailabilitySqlQueryConstant.findAllSaturday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SUNDAY,
                    CarparkAvailabilitySqlQueryConstant.findAllSunday)
    );

    private static Map<DayOfWeekConstant, String> mapFetchQueries = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.MONDAY,
                    CarparkAvailabilitySqlQueryConstant.fetchQueriesMonday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.TUESDAY,
                    CarparkAvailabilitySqlQueryConstant.fetchQueriesTuesday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.WEDNESDAY,
                    CarparkAvailabilitySqlQueryConstant.fetchQueriesWednesday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.THURSDAY,
                    CarparkAvailabilitySqlQueryConstant.fetchQueriesThursday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.FRIDAY,
                    CarparkAvailabilitySqlQueryConstant.fetchQueriesFriday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SATURDAY,
                    CarparkAvailabilitySqlQueryConstant.fetchQueriesSaturday),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SUNDAY,
                    CarparkAvailabilitySqlQueryConstant.fetchQueriesSunday)
    );

    public static String getMapFindAllQuery(DayOfWeekConstant day, int hour) {
        return mapFindAllQueries.get(day) + " WHERE hour=" + hour;
    }

    public static String getMapCarparkInfoQuery(DayOfWeekConstant day, String carpark_id) {
        return mapFindAllQueries.get(day) + " WHERE carpark_id='" + carpark_id + "'";
    }

    public static String getMapFetchQuery(DayOfWeekConstant day) {
        return mapFetchQueries.get(day);
    }

}
