package com.aksomat.geo.geometry;

import com.aksomat.geo.utils.Model;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

  public static class greatCirclePointTest {
    @Test
    public void simpleTest() {
      Point p0 = new Point(0,0);
      double expectedLat = 0;
      double expectedLon = 45;
      double lonDist = Math.toRadians(expectedLon) * Model.EARTH_MEAN_RADIUS;
      Point actual = Point.greatCirclePoint(p0, 90, lonDist);
      double delta = 1E-12;
      Assert.assertEquals(expectedLat, actual.getLat(), delta);
      Assert.assertEquals(expectedLon, actual.getLon(), delta);
    }
  }

}