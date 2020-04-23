package com.aksomat.geo.distance;

import com.aksomat.geo.geometry.LineString;
import com.aksomat.geo.geometry.Point;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

// TODO Make this code and comments clearer
// TODO Write tests for distance calculations

public final class Utils {
  /**
   * The mean radius of the earth in meters. Can be used for algorithms that assume earth is a
   * sphere.
   */
  public static final int EARTH_MEAN_RADIUS = 6_371_009;

  /** Don't let anyone instantiate this class. */
  private Utils() {}

  private static double hav(double x) {
    double y = sin(x / 2) * sin(x / 2);
    return y;
  }

  private static double lawOfHaversines(double a, double b, double C) {
    double h = hav(a - b) + (sin(a) * sin(b) * hav(C));
    return h;
  }

  private static double ahav(double h) {
    /*
     ahav(x) = 2 * asin(√x) = 2 * atan(√x / √(1 -x))
     atan2: https://en.wikipedia.org/wiki/Atan2
    */
    double ahav = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
    return ahav;
  }

  /**
   * Calculates the planar distance between the two points by projecting them on a plane. This
   * method assumes that earth is a sphere. Only accurate for short distances
   *
   * @param p1
   * @param p2
   * @return
   */
  public static double distPlanar(Point p1, Point p2) {
    double R = EARTH_MEAN_RADIUS;
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
    return distPlanar(p1, p2);
  }

  /**
   * Private method that calculates the radian distance between two points
   *
   * @param p1
   * @param p2
   * @return
   */
  private static double distHaversineRAD(Point p1, Point p2) {
    double lat1 = Math.toRadians(p1.getLat());
    double lon1 = Math.toRadians(p1.getLon());
    double lat2 = Math.toRadians(p2.getLat());
    double lon2 = Math.toRadians(p2.getLon());
    double latDist = lat2 - lat1;
    double lonDist = lon2 - lon1;

    /*
     Law of haversines
     Haversine of unit-circle distance (radians).
     See https://en.wikipedia.org/wiki/Versine
    */
    // Using that sin(PI/2 - x) = cos(x)
    double h = hav(latDist) + cos(lat1) * cos(lat2) * hav(lonDist);

    double c = ahav(h); // unit circle distance (radians)
    return c;
  }

  /** Calculates the distance between two points using the Haversine formula. */
  public static double distHaversine(Point p1, Point p2) {
    double c = distHaversineRAD(p1, p2);
    double distance = c * EARTH_MEAN_RADIUS;
    return distance;
  }

  private static double calculateDirection(double c, Point start, Point end) {
    double a = Math.toRadians(90 - start.getLat());
    double b = Math.toRadians(90 - end.getLat());
    double havB = (hav(b) - hav(c - a)) / (sin(c) * sin(a));
    double direction;
    if (start.getLon() < end.getLon()) {
      direction = ahav(havB);
    } else {
      direction = (2 * Math.PI) - ahav(havB);
    }
    return direction;
  }

  private static Point greatCirclePoint(Point startPoint, double direction, double distance) {
    double coLatStart = Math.toRadians(90 - startPoint.getLat());
    double coLatPoint;
    double lonDist;
    double lon;
    if (direction < Math.PI) {
      coLatPoint = ahav(lawOfHaversines(distance, coLatStart, direction));
      lonDist =
          ahav(
              (hav(distance) - hav(coLatStart - coLatPoint)) / (sin(coLatStart) * sin(coLatPoint)));
      lon = startPoint.getLon() + Math.toDegrees(lonDist);
    } else {
      coLatPoint = ahav(lawOfHaversines(coLatStart, distance, 2 * Math.PI - direction));
      lonDist =
          ahav(
              (hav(distance) - hav(coLatPoint - coLatStart)) / (sin(coLatPoint) * sin(coLatStart)));
      lon = startPoint.getLon() - Math.toDegrees(lonDist);
    }
    double lat = 90 - Math.toDegrees(coLatPoint);
    Point point = new Point(lat, lon);
    return point;
  }

  /**
   * Calculates and creates new points linearly spaced on a geodesic line between the two input
   * parameters.
   *
   * <p>This function is implemented assuming the earth is a perfect sphere (which it is not). It
   * should be fairly accurate for short distances.
   *
   * @param start the starting point
   * @param end the end point
   * @param n the number of points that should be in the new LineString
   * @return linspaced a linearly spaced list of points between start and end
   */
  public static LineString linspace(Point start, Point end, int n) {
    // TODO validate that n is >=2
    LineString lineString = new LineString();
    lineString.add(start);

    double c = distHaversineRAD(start, end);
    double direction = calculateDirection(c, start, end);
    double space = c / (n - 1);
    for (int i = 1; i < n - 1; i++) {
      Point child = greatCirclePoint(start, direction, i * space);
      lineString.add(child);
    }
    lineString.add(end);
    return lineString;
  }
}
