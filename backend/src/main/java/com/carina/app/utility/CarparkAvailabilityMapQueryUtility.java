package com.carina.app.utility;

import com.carina.app.constant.CarparkAvailabilitySQLQueryConstant;
import com.carina.app.constant.DayOfWeekConstant;

import java.util.AbstractMap;
import java.util.Map;

public class CarparkAvailabilityMapQueryUtility {

    private static Map<DayOfWeekConstant, String> mapFindAllQueries = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.MONDAY, CarparkAvailabilitySQLQueryConstant.FIND_ALL_MONDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.TUESDAY, CarparkAvailabilitySQLQueryConstant.FIND_ALL_TUESDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.WEDNESDAY, CarparkAvailabilitySQLQueryConstant.FIND_ALL_WEDNESDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.THURSDAY, CarparkAvailabilitySQLQueryConstant.FIND_ALL_THURSDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.FRIDAY, CarparkAvailabilitySQLQueryConstant.FIND_ALL_FRIDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SATURDAY, CarparkAvailabilitySQLQueryConstant.FIND_ALL_SATURDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SUNDAY, CarparkAvailabilitySQLQueryConstant.FIND_ALL_SUNDAY)
    );

    private static Map<DayOfWeekConstant, String> mapFetchQueries = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.MONDAY, CarparkAvailabilitySQLQueryConstant.FETCH_QUERIES_MONDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.TUESDAY, CarparkAvailabilitySQLQueryConstant.FETCH_QUERIES_TUESDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.WEDNESDAY, CarparkAvailabilitySQLQueryConstant.FETCH_QUERIES_WEDNESDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.THURSDAY, CarparkAvailabilitySQLQueryConstant.FETCH_QUERIES_THURSDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.FRIDAY, CarparkAvailabilitySQLQueryConstant.FETCH_QUERIES_FRIDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SATURDAY, CarparkAvailabilitySQLQueryConstant.FETCH_QUERIES_SATURDAY),
            new AbstractMap.SimpleEntry<>(DayOfWeekConstant.SUNDAY, CarparkAvailabilitySQLQueryConstant.FETCH_QUERIES_SUNDAY)
    );

    public static String getMapFindAllQuery(DayOfWeekConstant day) {
        return mapFindAllQueries.get(day);
    }

    public static String getMapFetchQuery(DayOfWeekConstant day) {
        return mapFetchQueries.get(day);
    }

}
