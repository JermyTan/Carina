package com.carina.app.constant;

public class CarparkAvailabilitySQLQueryConstant {

    public final static String FIND_ALL_MONDAY = "SELECT * FROM carpark_availability_monday";
    public final static String FIND_ALL_TUESDAY = "SELECT * FROM carpark_availability_tuesday";
    public final static String FIND_ALL_WEDNESDAY = "SELECT * FROM carpark_availability_wednesday";
    public final static String FIND_ALL_THURSDAY = "SELECT * FROM carpark_availability_thursday";
    public final static String FIND_ALL_FRIDAY = "SELECT * FROM carpark_availability_friday";
    public final static String FIND_ALL_SATURDAY = "SELECT * FROM carpark_availability_saturday";
    public final static String FIND_ALL_SUNDAY = "SELECT * FROM carpark_availability_sunday";

    public final static String FETCH_QUERIES_MONDAY = "SELECT * FROM carpark_availability_monday " +
            "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public final static String FETCH_QUERIES_TUESDAY = "SELECT * FROM carpark_availability_tuesday " +
            "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public final static String FETCH_QUERIES_WEDNESDAY = "SELECT * FROM carpark_availability_wednesday " +
            "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public final static String FETCH_QUERIES_THURSDAY = "SELECT * FROM carpark_availability_thursday " +
            "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public final static String FETCH_QUERIES_FRIDAY = "SELECT * FROM carpark_availability_friday " +
            "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public final static String FETCH_QUERIES_SATURDAY = "SELECT * FROM carpark_availability_saturday " +
            "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
    public final static String FETCH_QUERIES_SUNDAY = "SELECT * FROM carpark_availability_sunday " +
            "where (area IN (:areas)) OR (development IN (:developments)) OR (lot_type IN (:lotTypes))";
}
