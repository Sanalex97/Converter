package Converters;

public class ConverterPSKtoGSKtoPSK {
    private double a;
    private double alpha;
    private double e;

    public ConverterPSKtoGSKtoPSK(double a, double alpha, double e) {
        this.a = a;
        this.alpha = alpha;
        this.e = e;
    }

    public double[] convertBLHtoXYZ(double b, double l, double h) {
        double x, y, z;
        // геодезические параметры
        double e2 = 2 * alpha * (1 - alpha); // квадрат эксцентриситета эллипсоида
        double N = a / (Math.sqrt(1 - e2 * Math.sin(b) * Math.sin(b)));  // радиус кривизны первого вертикала

        x = (N + h) * Math.cos(b) * Math.cos(l);
        y = (N + h) * Math.cos(b) * Math.sin(l);
        z = ((1 - e2) * N + h) * Math.sin(b);

        return new double[]{x, y, z};
    }

    public double[] convertXYZtoBLH(double x, double y, double z) {
        double B = 0, L = 0, H = 0;
        double r = Math.sqrt(x * x + y * y);                    // вспомогательная величина
        // todo анализ r
        if (r < 0.000000001) {
            B = (Math.PI * z) / (2 * Math.abs(z));
            L = 0;
            H = z * Math.sin(B) - a * Math.sqrt(1 - e * e * Math.sin(B) * Math.sin(B));

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
        else if (Math.abs(z) < 0.000000001) {
            B = 0;
            H = r - a;
        } else {
            double ro = Math.sqrt(x * x + y * y + z * z);
            double c = Math.asin(z / ro);
            double p = (e * e * a) / (2 * ro);

            //todo реализуем итеративный процесс
            double s1, s2 = 0;
            double b;
            double timeStarted = System.currentTimeMillis();
            // стоп-таймер 5 сек
            while (true) {
                s1 = s2;
                b = c + s1;
                s2 = Math.asin(p * Math.sin(2 * b) / (Math.sqrt(1 - e * e * Math.sin(b) * Math.sin(b))));
                if ((Math.abs(s2 - s1) < 0.0001) || ((System.currentTimeMillis() - timeStarted) > 25000)) {      // если заданная точность достигнута
                    B = b;

                    if (r / z < Math.pow(10, -8)) {
                        B = B - r / z;
                    }

                    H = r * Math.cos(B) + z * Math.sin(B) - a * Math.sqrt(1 - e * e * Math.sin(B) * Math.sin(B));

                    break;
                }
            }
        }
        return new double[]{B, L, H};
    }
}
