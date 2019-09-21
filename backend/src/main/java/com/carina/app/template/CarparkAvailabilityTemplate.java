package com.carina.app.template;

import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.model.CarparkAvailabilityMondayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Repository
public class CarparkAvailabilityTemplate {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<CarparkAvailabilityModel> findAll(String day) {
        final String FETCH_BY_QUERIES_SQL = "SELECT * FROM carpark_availability_monday ";
        return namedParameterJdbcTemplate.query(
                FETCH_BY_QUERIES_SQL,
                (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    public List<CarparkAvailabilityModel> getCarparkAvailabilityByQueries(
            Set<String> areas,
            Set<String> developments,
            Set<String> lotTypes
    ) {
        final String FETCH_BY_QUERIES_SQL = "SELECT * FROM carpark_availability_monday " +
                "where (area IN (:areas)) " +
                "OR (development IN (:developments)) " +
                "OR (lot_type IN (:lotTypes))";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("areas", areas);
        parameterSource.addValue("developments", developments);
        parameterSource.addValue("lotTypes", lotTypes);
        return namedParameterJdbcTemplate.query(
                FETCH_BY_QUERIES_SQL,
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
