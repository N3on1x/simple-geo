package com.aksomat.geo.utils;

import com.aksomat.geo.geometry.Point;
import org.junit.Assert;
import org.junit.Test;

public class NavigationTest {

  public static class AzimuthCalculationTest {
    @Test
    public void northPoleCrossing() {
      Point p1 = new Point(89.9, 0);
      Point p2 = new Point(89.9, 180);
      double expected = 0;
      double delta = 5E-15;
      double actual = Navigation.initialAzimuth(p1, p2);
      Assert.assertEquals(expected, actual, delta);
    }

    @Test
    public void northPole() {
      Point p = new Point(90, 0);
      double expected = 0;
      double delta = 5E-15;
      double actual = Navigation.initialAzimuth(p, p);
      Assert.assertEquals(expected, actual, delta);
    }

    @Test
    public void shouldBeEqual() {
      Point p1 = new Point(-34, -56);
      Point p2 = new Point(32, 154);
      double expected = 273.863333333;
      double delta = 0.0001;
      double actual = Navigation.initialAzimuth(p1, p2);
      Assert.assertEquals(expected, actual, delta);
    }
  }
}
