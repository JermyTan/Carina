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
        + "where lot_type IN (:lotTypes)";
    public static final String fetchQueriesTuesday = "SELECT * FROM carpark_availability_tuesday "
        + "where lot_type IN (:lotTypes)";
    public static final String fetchQueriesWednesday = "SELECT * FROM carpark_availability_wednesday "
        + "where lot_type IN (:lotTypes)";
    public static final String fetchQueriesThursday = "SELECT * FROM carpark_availability_thursday "
        + "where lot_type IN (:lotTypes)";
    public static final String fetchQueriesFriday = "SELECT * FROM carpark_availability_friday "
        + "where lot_type IN (:lotTypes)";
    public static final String fetchQueriesSaturday = "SELECT * FROM carpark_availability_saturday "
        + "where lot_type IN (:lotTypes)";
    public static final String fetchQueriesSunday = "SELECT * FROM carpark_availability_sunday "
        + "where lot_type IN (:lotTypes)";

    public static final String insertIntoFavourites = "INSERT INTO favourites (carpark_id, user_id) " +
        "VALUES (:carpark_id, :user_id)";

    public static final String deleteIntoFavourites = "DELETE FROM favourites WHERE carpark_id = :carpark_id and user_id = :user_id";

    public static final String findAllByCarparkIdMonday = "SELECT * FROM carpark_availability_monday WHERE carpark_id = :carpark_id";
    public static final String findAllByCarparkIdTuesday = "SELECT * FROM carpark_availability_tuesday WHERE carpark_id = :carpark_id";
    public static final String findAllByCarparkIdWednesday = "SELECT * FROM carpark_availability_wednesday WHERE carpark_id = :carpark_id";
    public static final String findAllByCarparkIdThursday = "SELECT * FROM carpark_availability_thursday WHERE carpark_id = :carpark_id";
    public static final String findAllByCarparkIdFriday = "SELECT * FROM carpark_availability_friday WHERE carpark_id = :carpark_id";
    public static final String findAllByCarparkIdSaturday = "SELECT * FROM carpark_availability_saturday WHERE carpark_id = :carpark_id";
    public static final String findAllByCarparkIdSunday = "SELECT * FROM carpark_availability_sunday WHERE carpark_id = :carpark_id";

}
