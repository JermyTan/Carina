package com.carina.app.template;

import com.carina.app.constant.CarparkAvailabilitySqlQueryConstant;
import com.carina.app.model.CarparkAvailabilityModelId;
import com.carina.app.payload.UserFavouritesRequestPayload;
import com.carina.app.utility.CarparkAvailabilityMapQueryUtility;
import com.carina.app.constant.DayOfWeekConstant;
import com.carina.app.model.CarparkAvailabilityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CarparkAvailabilityTemplate {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Returns the carparks queried for the instance in time.
     * @param day queried day of the week.
     * @return list of carparks queried.
     */
    public List<CarparkAvailabilityModel> findAll(DayOfWeekConstant day, int hour) {
        String findAllQuery = CarparkAvailabilityMapQueryUtility.getMapFindAllQuery(day, hour);
        return namedParameterJdbcTemplate.query(
                findAllQuery,
            (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    public List<CarparkAvailabilityModel> getCarparkInfo(DayOfWeekConstant day, String carpark_id) {
        String findAllQuery = CarparkAvailabilityMapQueryUtility.getMapCarparkInfoQuery(day, carpark_id);
        return namedParameterJdbcTemplate.query(
            findAllQuery,
            (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    /**
     * Returns the carparks if queried.
     * @param day queried day of the week.
     * @param lotTypes queried list of type of lots.
     * @return list of carparks queried.
     */
    public List<CarparkAvailabilityModel> getCarparkAvailabilityByQueries(
            DayOfWeekConstant day,
            Set<String> lotTypes
    ) {
        final String fetchByQuery = CarparkAvailabilityMapQueryUtility.getMapFetchQuery(day);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("lotTypes", lotTypes);
        return namedParameterJdbcTemplate.query(
                fetchByQuery,
                parameterSource,
            (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    private CarparkAvailabilityModel toCarparkAvailabilityModel(ResultSet rs) throws SQLException {
        CarparkAvailabilityModelId id = new CarparkAvailabilityModelId(
            rs.getString("carpark_id"),
            rs.getString("area"),
            rs.getString("development"),
            rs.getString("lot_type"),
            rs.getString("hour")
        );
        CarparkAvailabilityModel carparkAvailabilityModel = new CarparkAvailabilityModel();
        carparkAvailabilityModel.setLongitude(rs.getString("longitude"));
        carparkAvailabilityModel.setLatitude(rs.getString("latitude"));
        carparkAvailabilityModel.setAvailableLots(rs.getString("available_lots"));
        carparkAvailabilityModel.setAgency(rs.getString("agency"));
        carparkAvailabilityModel.setTimestamp(rs.getString("timestamp"));
        carparkAvailabilityModel.setCarparkAvailabilityId(id);
        return carparkAvailabilityModel;
    }


}
