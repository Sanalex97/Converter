package Converters;

public class ConverterGSK {
    private double deltaB;
    private double deltaL;
    private double deltaH;
    private double aB;
    private double eB;
    //todo перепроверить перевод угловых секунд в радиаы
    private double ro = 206_264.80625 / 1000 * Math.PI / 3600 / 180; //

    public double[] convertAtoB(double bA, double lA, double hA, double aA, double eA) {
        double bB;
        double lB;
        double hB;

        double a = (aA + aB) / 2;
        double e2 = (eA * eA + eB * eB) / 2;
        double M = a * (1 - e2) * Math.pow((1 - e2 * Math.pow(Math.sin(bA), 2)), (float) -3 / 2);

      /*  deltaB = ro / (M + hA) * (n / a) * e2 * Math.sin(bA) * Math.cos(bA)
                * deltaA + ((n * n) / (a * a) + 1) * n * Math.sin(bA) * Math.cos(bA)
                * (deltaE2 / 2) - (deltaX * Math.cos(lA) + deltaY * Math.sin(lA))
                * Math.sin(bA) + deltaZ * Math.cos(bA) - omegaX * Math.sin(lA) * (1 + e2 * Math.cos(2 * bA))
                + omegaY * Math.cos(lA) * (1 + e2 * Math.cos(2 * bA) - ro * m * e2 * Math.sin(bA) * Math.cos(bA));

        deltaL = (ro / ((n + hA) * Math.cos(bA))) * (-deltaX * Math.sin(lA) + deltaY * Math.cos(lA))
                + Math.tan(1 - e2) * (omegaX * Math.cos(lA) + omegaY * Math.sin(lA)) - omegaZ;

        deltaH = -(a / n) * deltaA + n * Math.pow(Math.sin(bA), 2) * (deltaE2 / 2)
                + (deltaX * Math.cos(lA) + deltaY * Math.sin(lA)) * Math.cos(bA)
                + deltaZ * Math.sin(bA) - n * e2 * Math.cos(bA) * ((omegaX / ro) * Math.sin(lA)
                - (omegaY / ro) * Math.cos(lA)) + ((a * a) / n + hA) * m;*/

        bB = bA + deltaB;
        lB = lA + deltaL;
        hB = hA + deltaH;

        return new double[]{bB, lB, hB};

    }
}
