package Converters;

public class ConverterPSK {
   /* private final double deltaX; //a, alpha, e;
    private final double deltaY;
    private final double deltaZ;
    private final double omegaX;
    private final double omegaY;
    private final double omegaZ;
    private final double m;

    public ConverterPSK(TypeConverter typeConverter) {
        this.deltaX = typeConverter.getDeltaX();
        this.deltaY = typeConverter.getDeltaY();
        this.deltaZ = typeConverter.getDeltaZ();
        this.omegaX = typeConverter.getOmegaX() / 1000 * Math.PI / 3600 / 180; // перевод градусов в радианы
        this.omegaY = typeConverter.getOmegaY() / 1000 * Math.PI / 3600 / 180;
        this.omegaZ = typeConverter.getOmegaZ() / 1000 * Math.PI / 3600 / 180;
        this.m = typeConverter.getM() / 1000000;                               // коэффициент
    }*/

    public static double[] convertAtoB(TypeConverter typeConverter, double xA, double yA, double zA) {
        double xB, yB, zB;
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
}
