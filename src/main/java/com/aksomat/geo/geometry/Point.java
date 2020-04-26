package com.aksomat.geo.geometry;

import com.aksomat.geo.utils.Model;

import static java.lang.Math.sin;

/**
 * A point with a latitude and longitude
 */
public class Point {
  private final double lat;
  private final double lon;
  private String description = null;

  /**
   * Constructs a 2-D Point with a latitude and a longitude
   *
   * @param lat the latitude
   * @param lon the longitude
   */
  public Point(double lat, double lon) {
    // FIXME Validate paramaters within [-90, 90]
    this.lat = lat;
    this.lon = lon;
  }

  public Point(double lat, double lon, String description) {
    // TODO Validate arguments and throw IllegalArgumentException
    this.lat = lat;
    this.lon = lon;
    this.description = description;
  }

  @Override
  public String toString() {
    String strLat = String.format("%.6f", lat);
    String strLon = String.format("%.6f", lon);
    if (description != null && !description.isBlank()) {
      return "Point{Lon=" + strLon + ", Lat=" + strLat + ", Description: " + description + "}";
    } else {
      return "Point{Lon=" + strLon + ", Lat=" + strLat + "}";
    }
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the latitude of a point in degrees
   *
   * @return the latitude
   */
  public double getLat() {
    return lat;
  }

  /**
   * Get the longitude of a point in degrees
   *
   * @return the longitude
   */
  public double getLon() {
    return lon;
  }

  /**
   * Get the point values as an array
   *
   * @return the latitude and longitude in an array where the first item is the latitude, and the
   * second is the longitude
   */
  public double[] getArray() {
    double[] points = {lat, lon};
    return points;
  }

  public static Point greatCirclePoint(Point startPoint, double azimuth, double distance) {
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
