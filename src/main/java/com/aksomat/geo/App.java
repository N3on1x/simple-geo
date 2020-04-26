package com.aksomat.geo;

import com.aksomat.geo.utils.Distance;
import com.aksomat.geo.geometry.LineString;
import com.aksomat.geo.geometry.Point;

//

/** App entry point */
public class App {
  public static void main(String[] args) {
    // Samfundet
    // Lat: 63.4225, Lon: 10.3954
    Point samfundet = new Point(63.4225, 10.3954, "Studentersamfundet in Trondheim");
//    Point saoPaolo = new Point(-23.579091596182867, -46.65893554687499);
    Point vancouver = new Point(49.25346477497736, -123.1622314453125);

//    double planarDistance = Distance.distPlanar(vancouver, samfundet);
    double haversineDistance = Distance.distHaversine(vancouver, samfundet);

//    System.out.println("Planar distance: " + planarDistance + " m");
    System.out.println("Haversine distance: " + haversineDistance + " m");
//    System.out.println("Difference: " + (haversineDistance - planarDistance) + '\n');

    LineString intervals = new LineString(samfundet, vancouver, 9);
    System.out.println(intervals);

    //
    //        try (JsonReader reader = new JsonReader(new BufferedReader(new
    // FileReader("myHouse.geojson")))) {
    //            JsonElement json = JsonParser.parseReader(reader);
    //            Gson gson = new GsonBuilder()
    //                    .registerTypeAdapterFactory(new GeometryAdapterFactory())
    //                    .create();
    //            FeatureCollection fc = gson.fromJson(json, FeatureCollection.class);
    //            Point myHouse = (Point) fc.features().get(0).geometry();
    //            System.out.println(myHouse.toString());
    //        } catch (FileNotFoundException e) {
    //            e.printStackTrace();
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }

    //        GeoJSONReader reader = new GeoJSONReader(ctx, factory);
  }
}
