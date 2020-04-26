package com.aksomat.geo.utils;

import com.aksomat.geo.geometry.Point;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

// TODO Make this code and comments clearer
// TODO Write tests for distance calculations

public final class Distance {

  /** Don't let anyone instantiate this class. */
  private Distance() {}

  /**
   * Calculates the planar distance between the two points by projecting them on a plane. This
   * method assumes that earth is a sphere. Only accurate for short distances
   *
   * @param p1
   * @param p2
   * @return
   */
  public static double distPlanar(Point p1, Point p2) {
    double R = Model.EARTH_MEAN_RADIUS;
    double lat1 = Math.toRadians(p1.getLat());
    double lon1 = Math.toRadians(p1.getLon());
    double lat2 = Math.toRadians(p2.getLat());
    double lon2 = Math.toRadians(p2.getLon());
    double latMean = (lat1 + lat2) / 2;

    double latDist = lat2 - lat1;
    double lonDist = lon2 - lon1;

    double distance =
        R * Math.sqrt(latDist * latDist + cos(latMean) * lonDist * cos(latMean) * lonDist);
    return distance;
  }

  public static double distance(Point p1, Point p2) {
    return distHaversine(p1, p2);
  }

  /**
   * Private method that calculates the radian distance between two points
   *
   * @param p1
   * @param p2
   * @return
   */
  private static double distHaversineRAD(Point p1, Point p2) {
    double coLat1 = Math.toRadians(90 - p1.getLat());
    double lon1 = Math.toRadians(p1.getLon());
    double coLat2 = Math.toRadians(90 - p2.getLat());
    double lon2 = Math.toRadians(p2.getLon());
    double lonDiff = lon2 - lon1;

    double h =
        Model.lawOfHaversines(coLat1, coLat2, lonDiff); // Haversine of great circle radii distance
    double c = Model.ahav(h);
    return c;
  }

  /** Calculates the distance between two points using the Haversine method. */
  public static double distHaversine(Point p1, Point p2) {
    double c = distHaversineRAD(p1, p2);
    double distance = c * Model.EARTH_MEAN_RADIUS;
    return distance;
  }

  private static Point greatCirclePoint(Point startPoint, double azimuth, double distance) {
    double coLatStart = Math.toRadians(90 - startPoint.getLat());
    double coLatPoint;
    double lonDist;
    double lon;
    // FIXME Should use n-vector instead of Lat, Lon to avoid common problems
    if (azimuth < Math.PI) {
      coLatPoint = Model.ahav(Model.lawOfHaversines(distance, coLatStart, azimuth));
      lonDist =
          Model.ahav(
              (Model.hav(distance) - Model.hav(coLatStart - coLatPoint))
                  / (sin(coLatStart) * sin(coLatPoint)));
      lon = startPoint.getLon() + Math.toDegrees(lonDist);
    } else {
      coLatPoint = Model.ahav(Model.lawOfHaversines(coLatStart, distance, 2 * Math.PI - azimuth));
      lonDist =
          Model.ahav(
              (Model.hav(distance) - Model.hav(coLatPoint - coLatStart))
                  / (sin(coLatPoint) * sin(coLatStart)));
      lon = startPoint.getLon() - Math.toDegrees(lonDist);
    }
    double lat = 90 - Math.toDegrees(coLatPoint);
    Point point = new Point(lat, lon);
    return point;
  }
}
