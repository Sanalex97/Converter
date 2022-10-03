import java.util.HashMap;

public class CoordinateSystemManager {

/*    private CoordinateConverter WGS84toPZ9011, PZ9011toWGS84, PZ9011toSK95, PZ9011toSK42, SK42toPZ9011, SK95toPZ9011, SK42toSK95, SK95toSK42, SK95toWGS84, WGS84toSK95, SK42toWGS84, WGS84toSK42;
    private CoordinateConverter WGS84, PZ9011, SK95, SK42;
    private HashMap<String, CoordinateConverter> coordinateSystems, singleSystem;*/

    CoordinateSystemManager() {
     /*   //WGS84toPZ9011 = new CoordinateConverter(-0.013, 0.106, 0.022,-2.3, 3.54, -4.21, -0.008);
       //   WGS84toPZ9011 = new CoordinateConverter(+0.003, +0.001, 0, -0.019, +0.042, -0.002, 0); //эпоха 2001
        WGS84toPZ9011 = new CoordinateConverter(+0.0053, +0.0040, 0.0032, -0.035, +0.087, -0.036, 0); // эпоха 2001
        PZ9011toWGS84 = new CoordinateConverter(0.013, -0.106, -0.022, 2.3, -3.54, 4.21, 0.008); // эпоха 2010
        SK42toPZ9011 = new CoordinateConverter(23.557, -140.844, -79.778, -2.3, -346.46, -794.21, -0.228);
        PZ9011toSK42 = new CoordinateConverter(-23.557, 140.844, 79.778, 2.3, 346.46, 794.21, 0.228);
        SK95toPZ9011 = new CoordinateConverter(24.457, -130.784, -81.538, -2.3, 3.54, -134.21, -0.228);
        PZ9011toSK95 = new CoordinateConverter(-24.457, 130.784, 81.538, 2.3, -3.54, 134.21, 0.228);
        SK42toSK95 = new CoordinateConverter(-0.9, -10.06, 1.76, 0, -350, -660, 0);
        SK95toSK42 = new CoordinateConverter(0.9, 10.06, -1.76, 0, 350, 660, 0);
        SK95toWGS84 = new CoordinateConverter(24.47, -130.89, -81.56, 0, 0, -130, -0.220);
        WGS84toSK95 = new CoordinateConverter(-24.47, 130.89, 81.56, 0, 0, 130, 0.220);
        WGS84toSK42 = new CoordinateConverter(-23.57, 140.95, 79.8, 0, 350, 790, 0.220);
        SK42toWGS84 = new CoordinateConverter(23.57, -140.95, -79.8, 0, -350, -790, -0.220);
        ////
        coordinateSystems = new HashMap<>();
        coordinateSystems.put("WGS84toPZ9011", WGS84toPZ9011);
        coordinateSystems.put("PZ9011toWGS84", PZ9011toWGS84);
        coordinateSystems.put("SK42toPZ9011", SK42toPZ9011);
        coordinateSystems.put("PZ9011toSK42", PZ9011toSK42);
        coordinateSystems.put("SK95toPZ9011", SK95toPZ9011);
        coordinateSystems.put("PZ9011toSK95", PZ9011toSK95);
        coordinateSystems.put("SK42toSK95", SK42toSK95);
        coordinateSystems.put("SK95toSK42", SK95toSK42);
        coordinateSystems.put("WGS84toSK42", WGS84toSK42);
        coordinateSystems.put("SK42toWGS84", SK42toWGS84);
        coordinateSystems.put("WGS84toSK95", WGS84toSK95);
        coordinateSystems.put("SK95toWGS84", SK95toWGS84);
        //////////////////////////////////////////////////

        WGS84 = new CoordinateConverter(6_378_137, 0.0033528106647);
        PZ9011 = new CoordinateConverter(6_378_136, 1 / 298.257_84);
        SK95 = new CoordinateConverter(6_378_136, 1 / 298.257_84);
        SK42 = new CoordinateConverter(6_378_136, 1 / 298.257_84);
        ////
        singleSystem = new HashMap<>();
        singleSystem.put("WGS84", WGS84);
        singleSystem.put("PZ9011", PZ9011);
        singleSystem.put("SK95", SK95);
        singleSystem.put("SK42", SK42);
    }

    public double[] convertXYZ(String from, String to, double x, double y, double z) {
        if (from.equals(to)) return new double[]{x, y, z};
        return coordinateSystems.get(from + "to" + to).convertXYZ(x, y, z);
    }

    public double[] convertBLHtoXYZ(String from, double b, double l, double h) {
        return singleSystem.get(from).convertBLHtoXYZ(b, l, h);
    }

    public double[] convertXYZtoBLH(String from, double x, double y, double z) {
        return singleSystem.get(from).convertXYZtoBLH(x, y, z);
    }*/
    }
}