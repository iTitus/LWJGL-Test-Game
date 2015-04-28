package main.game.util;

public class Vector2d {

    public static final Vector2d O = new Vector2d(0, 0);
    public static final Vector2d X = new Vector2d(1, 0);
    public static final Vector2d Y = new Vector2d(0, 1);

    private final double x;
    private final double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d add(double d) {
        return new Vector2d(x + d, y + d);
    }

    public Vector2d add(Vector2d v) {
        return new Vector2d(x + v.x, y + v.y);
    }

    public double angleBetweenDeg(Vector2d v) {
        return Math.toDegrees(angleBetweenRad(v));
    }

    public double angleBetweenRad(Vector2d v) {
        return thetaRad() - v.thetaRad();
    }

    public Vector2d copy() {
        return new Vector2d(x, y);
    }

    public double cross(Vector2d v) {
        return x * v.getY() - y * v.getX();
    }

    public Vector2d div(double d) {
        return new Vector2d(x / d, y / d);
    }

    public Vector2d div(Vector2d v) {
        return new Vector2d(x / v.x, y / v.y);
    }

    public double dot(Vector2d v) {
        return x * v.getX() + y * v.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector2d) {
            Vector2d v = (Vector2d) o;
            return x == v.x && y == v.y;

        }
        return false;
    }

    public int getIntX() {
        return MathUtil.floor(x);
    }

    public int getIntY() {
        return MathUtil.floor(y);
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(x);
        bits ^= Double.doubleToLongBits(y) * 31;
        return (int) bits ^ (int) (bits >> 32);
    }

    public Vector2d lerp(Vector2d dest, double lerpFactor) {
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    public Vector2d mul(double d) {
        return new Vector2d(x * d, y * d);
    }

    public Vector2d mul(Vector2d v) {
        return new Vector2d(x * v.x, y * v.y);
    }

    public Vector2d normalize() {
        return div(getLength());
    }

    public Vector2d rotateDeg(double angleDeg) {
        return rotateRad(Math.toRadians(angleDeg));
    }

    public Vector2d rotateRad(double angleRad) {
        double cosX = Math.cos(angleRad);
        double sinY = Math.sin(angleRad);
        return new Vector2d(x * cosX - y * sinY, x * sinY + y * cosX);
    }

    public Vector2d sub(double d) {
        return new Vector2d(x - d, y - d);
    }

    public Vector2d sub(Vector2d v) {
        return new Vector2d(x - v.y, y - v.y);
    }

    public double thetaDeg() {
        return Math.toDegrees(thetaRad());
    }

    public double thetaRad() {
        return Math.atan2(y, x);
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }
}
