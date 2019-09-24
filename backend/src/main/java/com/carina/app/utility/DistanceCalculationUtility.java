package com.carina.app.utility;

import com.carina.app.constant.DistanceConstant;

public class DistanceCalculationUtility {

    // https://stackoverflow.com/questions/19412462/getting-distance-between-two-points-based-on-latitude-longitude
    /**
     * Returns the distance between source and destination.
     * @param lat1 latitude of source.
     * @param lon1 longitude of source.
     * @param lat2 latitude of destination.
     * @param lon2 longitude of destination.
     * @return distance between source and destination.
     */
    public static double getDistance(
            double lat1, double lon1,
            double lat2, double lon2
    ) {
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return DistanceConstant.RADIUS_OF_EARTH * c;
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
        return distance < radius;
    }

}
