package com.aksomat.geo.geometry;

import java.util.Iterator;
import java.util.LinkedList;

public class LineString extends LinkedList<Point> {
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
