package com.carina.app.template;

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

    public List<CarparkAvailabilityModel> findAll(DayOfWeekConstant day) {
        String FIND_ALL_QUERY = CarparkAvailabilityMapQueryUtility.getMapFindAllQuery(day);
        return namedParameterJdbcTemplate.query(
                FIND_ALL_QUERY,
                (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    public List<CarparkAvailabilityModel> getCarparkAvailabilityByQueries(
            DayOfWeekConstant day,
            Set<String> areas,
            Set<String> developments,
            Set<String> lotTypes
    ) {
        final String FETCH_BY_QUERY = CarparkAvailabilityMapQueryUtility.getMapFetchQuery(day);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("areas", areas);
        parameterSource.addValue("developments", developments);
        parameterSource.addValue("lotTypes", lotTypes);
        return namedParameterJdbcTemplate.query(
                FETCH_BY_QUERY,
                parameterSource,
                (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    private CarparkAvailabilityModel toCarparkAvailabilityModel (ResultSet rs) throws SQLException {
        CarparkAvailabilityModel carparkAvailabilityModel = new CarparkAvailabilityModel();
        carparkAvailabilityModel.setArea(rs.getString("area"));
        carparkAvailabilityModel.setLongitude(rs.getDouble("longitude"));
        carparkAvailabilityModel.setLatitude(rs.getDouble("latitude"));
        carparkAvailabilityModel.setCarparkId(rs.getString("carpark_id"));
        carparkAvailabilityModel.setDevelopment(rs.getString("development"));
        carparkAvailabilityModel.setAvailableLots(rs.getInt("available_lots"));
        carparkAvailabilityModel.setLotType(rs.getString("lot_type"));
        carparkAvailabilityModel.setAgency(rs.getString("agency"));
        carparkAvailabilityModel.setTimestamp(rs.getLong("timestamp"));
        return carparkAvailabilityModel;
    }

}
