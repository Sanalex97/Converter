public class TypeConverter {
    private int id;
    private String systemA;
    private String systemB;
    private double deltaX; //a, alpha, e;
    private double deltaY;
    private double deltaZ;
    private double omegaX;
    private double omegaY;
    private double omegaZ;
    private double m;

    public TypeConverter() {
    }

    public int getId() {
        return id;
    }

    public String getSystemA() {
        return systemA;
    }

    public String getSystemB() {
        return systemB;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public double getDeltaZ() {
        return deltaZ;
    }

    public double getOmegaX() {
        return omegaX;
    }

    public double getOmegaY() {
        return omegaY;
    }

    public double getOmegaZ() {
        return omegaZ;
    }

    public double getM() {
        return m;
    }

    @Override
    public String toString() {
        return "TypeConverter{" +
                "id=" + id +
                ", systemA='" + systemA + '\'' +
                ", systemB='" + systemB + '\'' +
                ", deltaX=" + deltaX +
                ", deltaY=" + deltaY +
                ", deltaZ=" + deltaZ +
                ", omegaX=" + omegaX +
                ", omegaY=" + omegaY +
                ", omegaZ=" + omegaZ +
                ", m=" + m +
                '}';
    }
}
