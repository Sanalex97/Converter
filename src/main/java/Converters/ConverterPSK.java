package Converters;

public class ConverterPSK {
    private double deltaX; //a, alpha, e;
    private double deltaY;
    private double deltaZ;
    private double omegaX;
    private double omegaY;
    private double omegaZ;
    private double m;

    public ConverterPSK(double deltaX, double deltaY, double deltaZ, double omegaX, double omegaY, double omegaZ, double m) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.omegaX = omegaX / 1000 * Math.PI / 3600 / 180; // ������� �������� � �������
        this.omegaY = omegaY / 1000 * Math.PI / 3600 / 180;
        this.omegaZ = omegaZ / 1000 * Math.PI / 3600 / 180;
        this.m = m / 1000000;                               // �����������
    }

    public double[] convertAtoB(double xA, double yA, double zA) {
        double xB, yB, zB;
        //todo ��������� ����������� m � �������� �����������
        xA = xA * (1 + m);
        yA = yA * (1 + m);
        zA = zA * (1 + m);
        //todo ����������� ������� (������ ���������� �� ������� ����������� � ������������)
        xB = xA + yA * this.omegaZ + zA * (-this.omegaY);
        yB = -this.omegaZ * xA + yA + this.omegaX * zA;
        zB = this.omegaY * xA + (-this.omegaX) * yA + zA;
        //todo �������� �������� �������� �����������������
        xB = xB + this.deltaX;
        yB = yB + this.deltaY;
        zB = zB + this.deltaZ;

        return new double[]{xB, yB, zB};
    }
}
