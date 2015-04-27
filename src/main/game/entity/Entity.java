package main.game.entity;

import main.game.render.EntityRenderer;
import main.game.render.ISprite;
import main.game.tile.Tile;
import main.game.util.MathUtil;
import main.game.world.World;

public abstract class Entity {

    private boolean isDead = false;
    protected double prevPosX, prevPosY, posX, posY, sizeX, sizeY, motionX, motionY;
    protected World world;

    public Entity(World world) {
        this.world = world;
    }

    public double getDistance(double x, double y) {
        return MathUtil.pythagoras(posX - x, posY - y);
    }

    public double getDistance(Entity e) {
        return getDistance(e.getPosXCentered(), e.getPosYCentered());
    }

    public double getMotionX() {
        return motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosXCentered() {
        return posX + sizeX / 2;
    }

    public int getPosXTiled() {
        return MathUtil.floor(getPosXCentered());
    }

    public double getPosY() {
        return posY;
    }

    public double getPosYCentered() {
        return posY + sizeY / 2;
    }

    public int getPosYTiled() {
        return MathUtil.floor(getPosYCentered());
    }

    public double getPrevPosX() {
        return prevPosX;
    }

    public double getPrevPosY() {
        return prevPosY;
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public abstract ISprite getSprite();

    public World getWorld() {
        return world;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isInside(double posX, double posY, double sizeX, double sizeY) {
        return Math.max(posX, this.posX) < Math.min(posX + sizeX, this.posX + this.sizeX) && Math.max(posY, this.posY) < Math.min(posY + sizeY, this.posY + this.sizeY);
    }

    public boolean isInside(Entity e) {
        return isInside(e.posX, e.posY, e.sizeX, e.sizeY);
    }

    public void move(double dx, double dy) {
        int rX1 = MathUtil.floor(posX + dx);
        int rY1 = MathUtil.floor(posY + dy);
        Tile r1 = world.getTileAt(rX1, rY1);
        int rX2 = MathUtil.floor(posX + sizeX + dx);
        int rY2 = MathUtil.floor(posY + dy);
        Tile r2 = world.getTileAt(rX2, rY2);
        int rX3 = MathUtil.floor(posX + dx);
        int rY3 = MathUtil.floor(posY + sizeY + dy);
        Tile r3 = world.getTileAt(rX3, rY3);
        int rX4 = MathUtil.floor(posX + sizeX + dx);
        int rY4 = MathUtil.floor(posY + sizeY + dy);
        Tile r4 = world.getTileAt(rX4, rY4);

        double dposX = 0;
        double dposY = 0;
        if ((r1 == null || !(r1.isSolid() && MathUtil.isInside(rX1, rY1, 1, 1, posX + dx, posY + dy, sizeX, sizeY))) && (r2 == null || !(r2.isSolid() && MathUtil.isInside(rX2, rY2, 1, 1, posX + dx, posY + dy, sizeX, sizeY)))
                && (r3 == null || !(r3.isSolid() && MathUtil.isInside(rX3, rY3, 1, 1, posX + dx, posY + dy, sizeX, sizeY))) && (r4 == null || !(r4.isSolid() && MathUtil.isInside(rX4, rY4, 1, 1, posX + dx, posY + dy, sizeX, sizeY)))) {
            dposX = dx;
            dposY = dy;
        } else {
            dposX = dx != 0 ? dx < 0 ? rX1 + 1 - posX : rX2 - (posX + sizeX) : 0;
            dposY = dy != 0 ? dy < 0 ? rY1 + 1 - posY : rY3 - (posY + sizeY) : 0;
        }
        posX += dposX;
        posY += dposY;
        if (r1 != null) {
            r1.onWalkOn(world, rX1, rY1, this);
        }
        if (r2 != null && (rX2 != rX1 || rY2 != rY1)) {
            r2.onWalkOn(world, rX2, rY2, this);
        }
        if (r3 != null && (rX3 != rX1 || rY3 != rY1) && (rX3 != rX2 || rY3 != rY2)) {
            r3.onWalkOn(world, rX3, rY3, this);
        }
        if (r4 != null && (rX4 != rX1 || rY4 != rY1) && (rX4 != rX2 || rY4 != rY2) && (rX4 != rX3 || rY4 != rY3)) {
            r4.onWalkOn(world, rX4, rY4, this);
        }
        for (Entity e : world.getEntitiesExcludingEntityAt(posX, posY, sizeX, sizeY, this)) {
            e.onCollide(this);
        }
    }

    public void onCollide(Entity e) {
        // TODO
    }

    public void render() {
        EntityRenderer.renderStandardEntity(this);
    }

    public void setDead() {
        isDead = true;
    }

    public void setMotion(double motionX, double motionY) {
        setMotionX(motionX);
        setMotionY(motionY);
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public void setPosition(double posX, double posY) {
        setPosX(posX);
        setPosY(posY);
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setSize(double sizeX, double sizeY) {
        setSizeX(sizeX);
        setSizeY(sizeY);
    }

    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void update() {
        prevPosX = posX;
        prevPosY = posY;
        move(motionX, motionY);
    }

}
