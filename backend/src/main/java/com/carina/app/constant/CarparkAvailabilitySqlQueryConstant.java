package com.carina.app.constant;

public class CarparkAvailabilitySqlQueryConstant {

    public static final String findAllMonday = "SELECT * FROM carpark_availability_monday";
    public static final String findAllTuesday = "SELECT * FROM carpark_availability_tuesday";
    public static final String findAllWednesday = "SELECT * FROM carpark_availability_wednesday";
    public static final String findAllThursday = "SELECT * FROM carpark_availability_thursday";
    public static final String findAllFriday = "SELECT * FROM carpark_availability_friday";
    public static final String findAllSaturday = "SELECT * FROM carpark_availability_saturday";
    public static final String findAllSunday = "SELECT * FROM carpark_availability_sunday";

    public static final String fetchQueriesMonday = "SELECT * FROM carpark_availability_monday "
            + "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public static final String fetchQueriesTuesday = "SELECT * FROM carpark_availability_tuesday "
            + "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public static final String fetchQueriesWednesday = "SELECT * FROM carpark_availability_wednesday "
            + "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public static final String fetchQueriesThursday = "SELECT * FROM carpark_availability_thursday "
            + "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public static final String fetchQueriesFriday = "SELECT * FROM carpark_availability_friday "
            + "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public static final String fetchQueriesSaturday = "SELECT * FROM carpark_availability_saturday "
            + "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public static final String fetchQueriesSunday = "SELECT * FROM carpark_availability_sunday "
            + "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
}
