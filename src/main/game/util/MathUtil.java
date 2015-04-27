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

    public static boolean isInside(double posX1, double posY1, double sizeX1, double sizeY1, double posX2, double posY2, double sizeX2, double sizeY2) {
        return Math.max(posX2, posX1) < Math.min(posX2 + sizeX2, posX1 + sizeX1) && Math.max(posY2, posY1) < Math.min(posY2 + sizeY2, posY1 + sizeY1);
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
