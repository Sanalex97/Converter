package Converters;

public class TypeConverter {
    private int id;
    private String systemA;
    private double deltaX; //a, alpha, e;
    private double deltaY;
    private double deltaZ;
    private double omegaX;
    private double omegaY;
    private double omegaZ;
    private double m;

    public TypeConverter(double deltaX, double deltaY, double deltaZ, double omegaX, double omegaY, double omegaZ, double m) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.omegaX = omegaX ;//Math.PI / 3600 / 180;;
        this.omegaY = omegaY;//Math.PI / 3600 / 180;;
        this.omegaZ = omegaZ;//Math.PI / 3600 / 180;;
        this.m = m / 1000000;
    }

    public TypeConverter() {
    }

    public int getId() {
        return id;
    }

    public String getSystemA() {
        return systemA;
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
        return (omegaX / 1000) * 4.85 / 1_000_000;
    }

    public double getOmegaY() {
        return (omegaY / 1000) * 4.85 / 1_000_000;
    }

    public double getOmegaZ() {
        return (omegaZ / 1000) * 4.85 / 1_000_000;
    }

    public double getM() {
        return m;
    }

    @Override
    public String toString() {
        return "TypeConverter{" +
                "id=" + id +
                ", systemA='" + systemA + '\'' +
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
