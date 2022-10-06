package Converters;

import static Constants.GeodezParam.*;

public class Converter {
    public static double[] convertPSKtoPSK(TypeConverter typeConverter, double xA, double yA, double zA) {
        double xB, yB, zB;
        System.out.println(typeConverter);
        //todo применить коэффициент m к исходным координатам
        xA = xA * (1 + typeConverter.getM());
        yA = yA * (1 + typeConverter.getM());
        zA = zA * (1 + typeConverter.getM());
        //todo перемножить матрицы (строка умножается на столбец поэлементно и складывается)
        xB = xA + yA * typeConverter.getOmegaZ() + zA * (-typeConverter.getOmegaY());
        yB = -typeConverter.getOmegaZ() * xA + yA + typeConverter.getOmegaX() * zA;
        zB = typeConverter.getOmegaX() * xA + (-typeConverter.getOmegaX()) * yA + zA;
        //todo добавить линейные элементы трансформирования
        xB = xB + typeConverter.getDeltaX();
        yB = yB + typeConverter.getDeltaY();
        zB = zB + typeConverter.getDeltaZ();

        return new double[]{xB, yB, zB};
    }

    public static double[] convertGSKtoGSK(TypeConverter typeConverter, double bA, double lA, double hA) {
        double bB;
        double lB;
        double hB;

       /* double a = (aA + aB) / 2;
        double e2 = (eA * eA + eB * eB) / 2;*/
        double v = E2 * Math.pow(Math.sin(bA), 2);
        double M = A * (1 - E2) * Math.pow((1 - v),-1.5);
        double N = A * Math.pow((1 - v), -0.5);
        double deltaA = 0;
        double deltaE2 = 0;

        double deltaB = RO / (M + hA) * (N / A) * E2 * Math.sin(bA) * Math.cos(bA)
                * deltaA + ((N * N) / (A * A) + 1) * N * Math.sin(bA) * Math.cos(bA)
                * (deltaE2 / 2) - (typeConverter.getDeltaX() * Math.cos(lA) + typeConverter.getDeltaY() * Math.sin(lA))
                * Math.sin(bA) + typeConverter.getDeltaZ() * Math.cos(bA) - typeConverter.getOmegaX() * Math.sin(lA)
                * (1 + E2 * Math.cos(2 * bA)) + typeConverter.getOmegaY()
                * Math.cos(lA) * (1 + E2 * Math.cos(2 * bA) - RO * typeConverter.getM()
                * E2 * Math.sin(bA) * Math.cos(bA));

        double deltaL = (RO / ((N + hA) * Math.cos(bA))) * (-typeConverter.getDeltaX()
                * Math.sin(lA) + typeConverter.getDeltaY() * Math.cos(lA))
                + Math.tan(1 - E2) * (typeConverter.getOmegaX() * Math.cos(lA) + typeConverter.getOmegaY()
                * Math.sin(lA)) - typeConverter.getOmegaZ();

        double deltaH = -(A / N) * deltaA + N * Math.pow(Math.sin(bA), 2) * (deltaE2 / 2)
                + (typeConverter.getDeltaX() * Math.cos(lA) + typeConverter.getDeltaY() * Math.sin(lA)) * Math.cos(bA)
                + typeConverter.getDeltaZ() * Math.sin(bA) - N * E2 * Math.cos(bA) * ((typeConverter.getOmegaX() / RO) * Math.sin(lA)
                - (typeConverter.getOmegaY() / RO) * Math.cos(lA)) + ((A * A) / N + hA) * typeConverter.getM();

        bB = bA + deltaB;
        lB = lA + deltaL ;
        hB = hA + deltaH;

        return new double[]{bB, lB, hB};
    }

    public static double[] convertPSKtoGSK(double x, double y, double z) {
        double B = 0, L = 0, H = 0;
        double r = Math.sqrt(x * x + y * y);                    // вспомогательная величина
        // todo анализ r
        if (r < 0.000000001) {
            B = (Math.PI * z) / (2 * Math.abs(z));
            L = 0;
            H = z * Math.sin(B) - A * Math.sqrt(1 - E2 * Math.sin(B) * Math.sin(B));

        } else if (r > 0.000000001) {
            double La = Math.abs(Math.asin(y / r));

            if (y >= 0 && x >= 0) {
                L = La;
            } else if (y >= 0 && x < 0) {
                L = Math.PI - La;
            } else if (y < 0 && x >= 0) {
                L = 2 * Math.PI - La;
            } else if (y < 0 && x < 0) {
                L = Math.PI + La;
            }
        }

        // todo анализ z
        if (Math.abs(z) < 0.000000001) {
            B = 0;
            H = r - A;
        } else {
            double ro = Math.sqrt(x * x + y * y + z * z);
            double c = Math.asin(z / ro);
            double p = (E2 * A) / (2 * ro);

            //todo реализуем итеративный процесс
            double s1, s2 = 0;
            double b;
            double timeStarted = System.currentTimeMillis();
            // стоп-таймер 5 сек
            while (true) {
                s1 = s2;
                b = c + s1;
                s2 = Math.asin(p * Math.sin(2 * b) / (Math.sqrt(1 - E2 * Math.sin(b) * Math.sin(b))));
                if ((Math.abs(s2 - s1) < 0.0001) || ((System.currentTimeMillis() - timeStarted) > 25000)) {      // если заданная точность достигнута
                    B = b;

                    if (r / z < Math.pow(10, -8)) {
                        B = B - r / z;
                    }

                    H = r * Math.cos(B) + z * Math.sin(B) - A * Math.sqrt(1 - E2 * Math.sin(B) * Math.sin(B));

                    break;
                }
            }
        }

        return new double[]{B , L , H};
    }

    public static double[] convertGSKtoPSK(double b, double l, double h) {
        double x, y, z;
        // геодезические параметры
        double e2 = 2 * ALFA * (1 - ALFA); // квадрат эксцентриситета эллипсоида
        double N = A / (Math.sqrt(1 - e2 * Math.sin(b) * Math.sin(b)));  // радиус кривизны первого вертикала

        x = (N + h) * Math.cos(b) * Math.cos(l);
        y = (N + h) * Math.cos(b) * Math.sin(l);
        z = ((1 - e2) * N + h) * Math.sin(b);

        return new double[]{x, y, z};
    }
}
