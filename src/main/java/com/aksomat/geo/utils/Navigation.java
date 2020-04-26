package com.aksomat.geo.utils;

import com.aksomat.geo.geometry.Point;

public final class Navigation {
  /** Don't let anyone instantiate this class. */
  private Navigation() {}

  public static double initialAzimuth(Point start, Point end) {
    double lat1RAD = Math.toRadians(start.getLat());
    double lon1RAD = Math.toRadians(start.getLon());
    double lat2RAD = Math.toRadians(end.getLat());
    double lon2RAD = Math.toRadians(end.getLon());
    double y = Math.sin(lon2RAD - lon1RAD) * Math.cos(lat2RAD);
    double x = Math.cos(lat1RAD) * Math.sin(lat2RAD) -
               Math.sin(lat1RAD)*Math.cos(lat2RAD)*Math.cos(lon2RAD - lon1RAD);
    double azimuth = Math.atan2(y,x);
    azimuth = (Math.toDegrees(azimuth) % 360 + 360) % 360;
    return azimuth;
  }


}
