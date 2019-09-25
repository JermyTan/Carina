package com.carina.app.utility;

import com.carina.app.constant.DistanceConstant;

public class DistanceCalculationUtility {

    private static double deg2rad (double n) {
        return (n * Math.PI) / 180.0;
    }

    // https://stackoverflow.com/questions/19412462/getting-distance-between-two-points-based-on-latitude-longitude
    public static double getDistance(
            double pointLat, double pointLng,
            double centerLat, double centerLng
    ) {
        double R = 6371000.0;
        double dlon = deg2rad(pointLat - centerLat);
        double dlat = deg2rad(pointLng - centerLng);
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
            Math.cos(deg2rad(centerLat)) * Math.cos(deg2rad(pointLat)) *
                Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Returns true if the given coordinate is within proximity.
     * @param lat1 latitude of source.
     * @param lon1 longitude of source.
     * @param lat2 latitude of destination.
     * @param lon2 longitude of destination.
     * @param radius radius from source.
     * @return true if within proximity.
     */
    public static boolean withinPromixity(
            double lat1, double lon1,
            double lat2, double lon2,
            double radius
    ) {
        double distance = getDistance(lat1, lon1, lat2, lon2);
        return distance <= radius;
    }

}
