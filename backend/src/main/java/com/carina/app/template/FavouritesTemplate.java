package com.carina.app.template;

import com.carina.app.constant.CarparkAvailabilitySqlQueryConstant;
import com.carina.app.constant.DayOfWeekConstant;
import com.carina.app.model.CarparkAvailabilityModel;
import com.carina.app.payload.UserFavouritesRequestPayload;
import com.carina.app.utility.CarparkAvailabilityMapQueryUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class FavouritesTemplate {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public int insertUserFavourite(UserFavouritesRequestPayload userFavouritesRequestPayload, Long user_id) {
        String insertQuery = CarparkAvailabilitySqlQueryConstant.insertIntoFavourites;
        // Creating map with all required params
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", user_id);
        paramMap.put("carpark_id", userFavouritesRequestPayload.getCarparkId());

        // Passing map containing named params
        return namedParameterJdbcTemplate.update(insertQuery, paramMap);
    }

    public int deleteUserFavourite(UserFavouritesRequestPayload userFavouritesRequestPayload, Long user_id) {
        String deleteQuery = CarparkAvailabilitySqlQueryConstant.deleteIntoFavourites;
        // Creating map with all required params
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", user_id);
        paramMap.put("carpark_id", userFavouritesRequestPayload.getCarparkId());
        // Passing map containing named params
        return namedParameterJdbcTemplate.update(deleteQuery, paramMap);
    }

}
