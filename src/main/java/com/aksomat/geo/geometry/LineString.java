package com.aksomat.geo.geometry;

import com.aksomat.geo.utils.Distance;
import com.aksomat.geo.utils.Model;
import com.aksomat.geo.utils.Navigation;

import java.util.Iterator;
import java.util.LinkedList;

public class LineString extends LinkedList<Point> {

  public LineString() {
    super();
  }

  /**
   * Constructs a
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
  public LineString(Point start, Point end, int n) {
    // TODO validate that n is >=2
    super();
    this.add(start);

    double c = Distance.distHaversine(start, end) / Model.EARTH_MEAN_RADIUS;

    double azimuth = Navigation.calculateAzimuth(c, start, end);
    double space = c / (n - 1);
    for (int i = 1; i < n - 1; i++) {
      Point child = Point.greatCirclePoint(start, azimuth, i * space);
      this.add(child);
    }
    this.add(end);
  }
  public String toString() {
    Iterator<Point> it = iterator();
    if (! it.hasNext())
      return "[]";

    StringBuilder sb = new StringBuilder();
    sb.append("[\n  ");
    for (;;) {
      Point e = it.next();
      sb.append(e);
      if (! it.hasNext())
        return sb.append('\n').append(']').toString();
      sb.append(',').append("\n  ");
    }
  }

}
