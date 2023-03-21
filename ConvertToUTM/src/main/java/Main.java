import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //   System.out.println(Arrays.toString(convertToUTM(5817280.657, 9546175.523, 52.482780, 51.679688, 9)));
        //System.out.println(Arrays.toString(convertToUTM(8885306.538, 40519392.56, 80, 238, 40)));
      //  System.out.println(Arrays.toString(convertToUTM(7040835.196, 14517089.291, 63.470145, 81.342773, 14)));
        // System.out.println(Math.ceil(4.2 / 8));
        //  System.out.println(Math.floorMod(3.2 / 8));
        System.out.println(Arrays.toString(UTMtoGSK(7100465.765, 353300, 32)));
    }

    private static String[] convertToUTM(double b, double l, int numZone) {
        //todo double[] xy = convertGSKtoGauss(b,l...);
        double k = 0.9996;
        double N = x * k;
        double E = (y - (int) (y / 1_000_000) * 1_000_000) * k + 200;

        int numZoneUtm = 0;

        if (numZone >= 1 && numZone <= 30) {
            numZoneUtm = numZone + 30;
        } else if (numZone >= 31 && numZone <= 60) {
            numZoneUtm = numZone - 30;
        }

        String nameZone = null;

        if (l >= 0 && l < 3 && b >= 56 && b <= 64) {
            nameZone = "V";
            numZoneUtm = 31;
        } else if (l >= 3 && l <= 12 && b >= 56 && b <= 64) {
            nameZone = "V";
            numZoneUtm = 32;
        } else {
            int numString = (int) Math.ceil(b / 8);

            switch (numString) {
                case 1:
                    nameZone = "N";
                    break;
                case 2:
                    nameZone = "P";
                    break;
                case 3:
                    nameZone = "Q";
                    break;
                case 4:
                    nameZone = "R";
                    break;
                case 5:
                    nameZone = "S";
                    break;
                case 6:
                    nameZone = "T";
                    break;
                case 7:
                    nameZone = "U";
                    break;
                case 8:
                    nameZone = "V";
                    break;
                case 9:
                    nameZone = "W";
                    break;
                case 10:
                    nameZone = "X";
                    break;
            }

            assert nameZone != null;
            if (nameZone.equals("X") && numZoneUtm < 38) {
                nameZone = null;
            }
        }

        return new String[]{String.valueOf(N), String.valueOf(E), String.valueOf(numZoneUtm), nameZone};
    }

    private static double[] UTMtoGSK(double N, double E, int numZoneUtm) {
        double x = N / 0.9996;
        double yy = (E - 200) / 0.9996;

        int numZone = 0;
        if (numZoneUtm >= 31 && numZoneUtm <= 60) {
            numZone = numZoneUtm - 30;
        } else if (numZoneUtm >= 1 && numZoneUtm <= 30) {
            numZone = numZoneUtm + 30;
        }

        if ((numZoneUtm == 31 || numZoneUtm == 32) && (N >= 6210141.324 && N <= 7100467.047)
                && (E >= 312928.614 && E <= 353304.823)) {
            numZone = 1;
        }

        double y = numZone * 1_000_000 + yy;

        return new double[]{x, y};

        //todo convertGaustoGSK(x,y) полученные значения выводим на экран
    }
}
