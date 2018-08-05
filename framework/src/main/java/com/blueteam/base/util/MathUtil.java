package com.blueteam.base.util;

/**
 * Created by huangqijun on 18/1/9.
 */
public class MathUtil {

    private static double EARTH_RADIUS = 6371.393;

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;

    }

    /**
     * 计算连个经纬度之间的距离
     *
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
