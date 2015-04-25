package main.game.util;

public class MathUtil {

    public static int ceil(double d) {
        int floored = floor(d);
        if (d == floored) {
            return floored;
        }
        return floored + 1;
    }

    public static int floor(double d) {
        return (int) d;
    }

    public static double pythagoras(double... doubles) {
        double r = 0;
        for (double d : doubles) {
            r += d * d;
        }
        return Math.sqrt(r);
    }

    public static int round(double d) {
        return floor(d + .5);
    }

}
