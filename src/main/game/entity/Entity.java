package main.game.entity;

import main.game.render.EntityRenderer;
import main.game.util.MathUtil;
import main.game.util.TileData;
import main.game.util.Vector2d;
import main.game.world.World;

public abstract class Entity {

    private boolean isDead, isSolid;
    protected double posX, posY, sizeX, sizeY;
    protected World world;

    public Entity(World world) {
        this.world = world;
        isDead = false;
        isSolid = true;
    }

    public boolean collides() {
        for (TileData t : world.getTilesAt(posX, posY, sizeX, sizeY)) {
            if (t == null || t.getTile() == null) {
                return true;
            }
            t.getTile().onWalkOn(world, t.getX(), t.getY(), this);
            if (t.getTile().isSolid()) {
                return true;
            }
        }
        for (Entity e : world.getEntitiesExcludingEntityAt(posX, posY, sizeX, sizeY, this)) {
            e.onCollide(this);
            if (isSolid && e.isSolid) {
                return true;
            }
        }
        return false;
    }

    public double getDistance(double x, double y) {
        return MathUtil.pythagoras(posX - x, posY - y);
    }

    public double getDistance(Entity e) {
        return getDistance(e.getPosXCentered(), e.getPosYCentered());
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

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

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

    public boolean isSolid() {
        return isSolid;
    }

    public void onCollide(Entity e) {
        // NO-OP
    }

    public void render() {
        EntityRenderer.renderEntity(this);
    }

    public void setDead() {
        isDead = true;
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

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Vector2d tryMove(double dx, double dy) {
        Vector2d original = new Vector2d(dx, dy);
        Vector2d v = original;
        setPosition(posX + v.getX(), posY + v.getY());
        // FIXME
        if (collides()) {
            do {
                if (v.getLength() <= 0) {
                    break;
                }
                v = v.div(2);
                setPosition(posX - v.getX(), posY - v.getY());
            } while (collides());
        }
        return v;
    }

    public void update() {
        tryMove(0, world.getGravity(this));
    }

}
