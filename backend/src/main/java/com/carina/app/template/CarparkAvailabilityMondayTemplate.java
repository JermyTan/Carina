package com.carina.app.template;

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
public class CarparkAvailabilityMondayTemplate {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<CarparkAvailabilityMondayModel> getCarparkAvailabilityByQueries(
            Set<String> days,
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
        return namedParameterJdbcTemplate.query(FETCH_BY_QUERIES_SQL, parameterSource, (resultSet, i) -> toCarparkAvailabilityMondayModel(resultSet));
    }

    private CarparkAvailabilityMondayModel toCarparkAvailabilityMondayModel (ResultSet rs) throws SQLException {
        CarparkAvailabilityMondayModel carparkAvailabilityMondayModel = new CarparkAvailabilityMondayModel();
        carparkAvailabilityMondayModel.setArea(rs.getString("area"));
        carparkAvailabilityMondayModel.setLongitude(rs.getDouble("longitude"));
        carparkAvailabilityMondayModel.setLatitude(rs.getDouble("latitude"));
        carparkAvailabilityMondayModel.setCarparkId(rs.getString("carpark_id"));
        carparkAvailabilityMondayModel.setDevelopment(rs.getString("development"));
        carparkAvailabilityMondayModel.setAvailableLots(rs.getInt("available_lots"));
        carparkAvailabilityMondayModel.setLotType(rs.getString("lot_type"));
        return carparkAvailabilityMondayModel;
    }


}
