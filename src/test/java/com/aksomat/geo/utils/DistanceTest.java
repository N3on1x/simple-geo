package com.aksomat.geo.utils;

import com.aksomat.geo.geometry.Point;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DistanceTest {

  public static class HaversineDistance {
    @Test
    public void northernHemisphere() {
      Point p1 = new Point(45, -1);
      Point p2 = new Point(45, 1);
      double expected = 157200;
      double delta = 0.01 * expected;
      // west-east
      double actual = Distance.distHaversine(p1, p2);
      assertEquals(expected, actual, delta);
      // east-west
      actual = Distance.distHaversine(p2, p1);
      assertEquals(expected, actual, delta);
    }

    @Test
    public void southernHemisphere() {
      Point p1 = new Point(-45, -1);
      Point p2 = new Point(-45, 1);
      double expected = 157200;
      double delta = 0.01 * expected;
      // west-east
      double actual = Distance.distHaversine(p1, p2);
      assertEquals(expected, actual, delta);
      // east-west
      actual = Distance.distHaversine(p2, p1);
      assertEquals(expected, actual, delta);
    }

    @Test
    public void crossingHemispheres() {
      Point p1 = new Point(45, -1);
      Point p2 = new Point(-45, 1);
      double expected = 10010000;
      double delta = 0.01 * expected;
      double actual = Distance.distHaversine(p1, p2);
      assertEquals(expected, actual, delta);
    }

    @Test
    public void singlePoint() {
      Point p = new Point(90,0);
      double expected = 0;
      double delta = 0;
      double actual = Distance.distHaversine(p,p);
      assertEquals(expected,actual,delta);
    }

    @Test
    public void northPoleCrossing() {
      Point p1 = new Point(89.9,0);
      Point p2 = new Point(89.9,180);
      double expected = 22240;
      double delta = 0.01 * expected;
      double actual = Distance.distHaversine(p1,p2);
      assertEquals(expected,actual,delta);
    }

    @Test
    public void southPoleCrossing() {
      Point p1 = new Point(-89.9,0);
      Point p2 = new Point(-89.9,180);
      double expected = 22240;
      double delta = 0.01 * expected;
      double actual = Distance.distHaversine(p1,p2);
      assertEquals(expected,actual,delta);
    }
  }
}
