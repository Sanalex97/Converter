
public class CoordinateConverter {
    public double deltaX, deltaY, deltaZ, omegaX, omegaY, omegaZ, m,
            a, alpha, e;


    // конструктор для задания коэффициентов для преобразования
    public CoordinateConverter(double dX, double dY, double dZ,
                               double omegaX, double omegaY, double omegaZ, double m) {
        // геоцентрические параметры (прямоугольные)
        this.deltaX = dX;                                           // метры
        this.deltaY = dY;
        this.deltaZ = dZ;
        this.omegaX = omegaX / 1000 * Math.PI / 3600 / 180;         // перевод градусов в радианы
        this.omegaY = omegaY / 1000 * Math.PI / 3600 / 180;
        this.omegaZ = omegaZ / 1000 * Math.PI / 3600 / 180;
        this.m = m / 1000000;                                       // коэффициент
    }

    public CoordinateConverter(double a, double alpha) {
        this.a = a;
        this.alpha = alpha;
        this.e = Math.sqrt(2 * alpha - alpha * alpha);                  // эксцентриситет эллипсоида
    }

    // ПАРАМЕТРЫ ЗЕМЛИ 1990 ГОДА. Приложение 4.
    public double[] convertXYZ(double x, double y, double z) {
        // инициализация конечных координат
        double x2, y2, z2;
        // применить коэффициент m к исходным координатам
        x = x * (1 + m);
        y = y * (1 + m);
        z = z * (1 + m);
        // перемножить матрицы (строка умножается на столбец поэлементно и складывается)
        x2 = x + y * this.omegaZ + z * (-this.omegaY);
        y2 = -this.omegaZ * x + y + this.omegaX * z;
        z2 = this.omegaY * x + (-this.omegaX) * y + z;
        // добавить линейные элементы трансформирования
        x2 = x2 + this.deltaX;
        y2 = y2 + this.deltaY;
        z2 = z2 + this.deltaZ;
        // вернуть массив координат
        return new double[]{x2, y2, z2};
    }

    // ПАРАМЕТРЫ ЗЕМЛИ 1990 ГОДА. Приложение 1.
    public double[] convertBLHtoXYZ(double b, double l, double h) {
        // инициализация конечных координат
        double x, y, z;
        // геодезические параметры
        double e2 = 2 * alpha - alpha * alpha;// радиус кривизны первого вертикала
        double N = this.a / (Math.sqrt(1 - e2 * Math.sin(b) * Math.sin(b)));
        // вычислить XYZ
        x = (N + h) * Math.cos(b) * Math.cos(l);
        y = (N + h) * Math.cos(b) * Math.sin(l);
        z = ((1 - e2) * N + h) * Math.sin(b);

        return new double[]{x, y, z};
    }

    // ПАРАМЕТРЫ ЗЕМЛИ 1990 ГОДА. Приложение 1.
    public double[] convertXYZtoBLH(double x, double y, double z) {
        // инициализация конечных координат
        double B = 0, L = 0, H = 0;
        double r = Math.sqrt(x * x + y * y);                    // вспомогательная величина
        // анализируем r
        if (r < 0.000000001) {
            B = Math.PI * z / (2 * Math.abs(z));
            L = 0;
            H = z * Math.sin(B) - a * Math.sqrt(1 - e * e * Math.sin(B) * Math.sin(B));
        } else {
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

/*            if(y < 0.0001){
                if(x > 0.0001) L = 2 * Math.PI - La;
                else L = Math.PI + La;
            }else{
                if(x < 0.0001) L = Math.PI - La;
                else L = La;
            }*/
        }
        if (Math.abs(z) < 0.000001) {
            B = 0;
            H = r - a;
        } else {
            double ro = Math.sqrt(x * x + y * y + z * z);
            double c = Math.asin(z / ro);
            double p = e * e * a / (2 * ro);
            // реализуем итеративный процесс
            double s1 = 0, s2 = 0;
            double b;
            double timeStarted = System.currentTimeMillis();
            // стоп-таймер 5 сек
            while (true) {
                s1 = s2;
                b = c + s1;
                s2 = Math.asin(p * Math.sin(2 * b) / (Math.sqrt(1 - e * e * Math.sin(b) * Math.sin(b))));
                if ((Math.abs(s2 - s1) < 0.0001) || ((System.currentTimeMillis() - timeStarted) > 25000)) {      // если заданная точность достигнута
                    B = b;
                    H = r * Math.cos(B) + z * Math.sin(B) - a * Math.sqrt(1 - e * e * Math.sin(B) * Math.sin(B));
                    break;
                }
            }
        }
        return new double[]{B, L, H};
    }
}