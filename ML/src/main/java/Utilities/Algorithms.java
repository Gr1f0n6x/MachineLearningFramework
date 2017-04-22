package Utilities;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public class Algorithms {

    public static double binpow(double a, int pow) {
        if(pow == 0) {
            return 1;
        }

        if(pow % 2 == 0) {
            double part = binpow(a, pow / 2);

            return part * part;
        } else {
            return a * binpow(a, pow - 1);
        }
    }
}
