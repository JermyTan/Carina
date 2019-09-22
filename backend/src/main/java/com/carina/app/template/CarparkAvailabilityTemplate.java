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
import java.util.List;
import java.util.Set;

@Repository
public class CarparkAvailabilityTemplate {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Returns the carparks queried for the instance in time.
     * @param day queried day of the week.
     * @return list of carparks queried.
     */
    public List<CarparkAvailabilityModel> findAll(DayOfWeekConstant day) {
        String findAllQuery = CarparkAvailabilityMapQueryUtility.getMapFindAllQuery(day);
        return namedParameterJdbcTemplate.query(
                findAllQuery,
            (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    /**
     * Returns the carparks if queried.
     * @param day queried day of the week.
     * @param areas queried list of areas.
     * @param developments queried list of developments.
     * @param lotTypes queried list of type of lots.
     * @return list of carparks queried.
     */
    public List<CarparkAvailabilityModel> getCarparkAvailabilityByQueries(
            DayOfWeekConstant day,
            Set<String> areas,
            Set<String> developments,
            Set<String> lotTypes
    ) {
        final String fetchByQuery = CarparkAvailabilityMapQueryUtility.getMapFetchQuery(day);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("areas", areas);
        parameterSource.addValue("developments", developments);
        parameterSource.addValue("lotTypes", lotTypes);
        return namedParameterJdbcTemplate.query(
                fetchByQuery,
                parameterSource,
            (resultSet, i) -> toCarparkAvailabilityModel(resultSet)
        );
    }

    private CarparkAvailabilityModel toCarparkAvailabilityModel(ResultSet rs) throws SQLException {
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
