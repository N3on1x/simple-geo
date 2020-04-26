package com.aksomat.geo.utils;

import com.aksomat.geo.geometry.Point;

import static java.lang.Math.sin;

public final class Navigation {
  /** Don't let anyone instantiate this class. */
  private Navigation() {}

  public static double calculateAzimuth(double c, Point start, Point end) {
    double a = Math.toRadians(90 - start.getLat());
    double b = Math.toRadians(90 - end.getLat());
    double havB = (Model.hav(b) - Model.hav(c - a)) / (sin(c) * sin(a));
    double bearing;
    if (start.getLon() < end.getLon()) {
      bearing = Model.ahav(havB);
    } else {
      bearing = (2 * Math.PI) - Model.ahav(havB);
    }
    return bearing;
  }


}
