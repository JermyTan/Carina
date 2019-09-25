package com.carina.app.service;

import com.carina.app.payload.LtaDataMallPayload;
import com.carina.app.payload.LtaDataMallValuePayload;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LtaDataMallGetRequestService {

    private static ArrayList<LtaDataMallValuePayload> getLtaDataMallValue(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        final String USER_AGENT = "Mozilla/5.0";

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("AccountKey", "PjPcZIN2SS+LjtXvfFlTIA==");

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String all = response.toString();
        LtaDataMallPayload data = new Gson().fromJson(all, LtaDataMallPayload.class);
        return data.getValue();
    }

    public static ArrayList<LtaDataMallValuePayload> getAllLtaDataMallValue() throws IOException {
        ArrayList<LtaDataMallValuePayload> payload = getLtaDataMallValue("http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2");
        ArrayList<LtaDataMallValuePayload> payload2 = getLtaDataMallValue("http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=500");
        ArrayList<LtaDataMallValuePayload> payload3 = getLtaDataMallValue("http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=1000");
        ArrayList<LtaDataMallValuePayload> payload4 = getLtaDataMallValue("http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=1500");
        ArrayList<LtaDataMallValuePayload> payload5 = getLtaDataMallValue("http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=2000");
        ArrayList<LtaDataMallValuePayload> payload6 = getLtaDataMallValue("http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2?$skip=2500");
        payload.addAll(payload2);
        payload.addAll(payload3);
        payload.addAll(payload4);
        payload.addAll(payload5);
        payload.addAll(payload6);

        return payload;
    }
}
