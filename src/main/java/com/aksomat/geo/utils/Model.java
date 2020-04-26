package com.aksomat.geo.utils;

import static java.lang.Math.sin;

public final class Model {

  /** Don't let anyone instantiate this class. */
  private Model() {}

  /**
   * The global mean radius of the earth rounded to nearest meter. Source: The R_1 of the IUGG
   * recommended mean radius: R_1 = (2a + b)/3 where a and b are the semi-major and semi-minor
   * axises of the earth's ellipsoidal shape. The values for a and b can be chosen for instance from
   * the WGS84 datum.
   *
   * <p>Distances calculated from this assumption has a maximum relative error below 1%
   */
  public static final int EARTH_MEAN_RADIUS = 6_371_009;

  public static double hav(double x) {
    double y = sin(x / 2) * sin(x / 2);
    return y;
  }

  public static double lawOfHaversines(double a, double b, double C) {
    double h = hav(a - b) + (sin(a) * sin(b) * hav(C));
    return h;
  }

  public static double ahav(double h) {
    /*
     ahav(x) = 2 * asin(√x) = 2 * atan(√x / √(1 -x))
     atan2: https://en.wikipedia.org/wiki/Atan2
    */
    double ahav = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h));
    return ahav;
  }
}
