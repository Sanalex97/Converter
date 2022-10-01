public class Main {
    private static CoordinateSystemManager coordinateSystemManager;
    public static void main(String[] args) {
        coordinateSystemManager = new CoordinateSystemManager();
        double[] coordinates = coordinateSystemManager.convertXYZ("WGS84", "PZ9011", 30, 30, 30);
        System.out.println("new x: " + coordinates[0] + "; new y: " + coordinates[1] + "; new z: " + coordinates[2]);

    }

}