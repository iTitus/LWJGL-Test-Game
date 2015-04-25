package main.game.entity;

import main.game.render.EntityRenderer;
import main.game.render.ISprite;
import main.game.util.MathUtil;
import main.game.world.World;

public abstract class Entity {

    private boolean isDead = false;
    private double prevPosX, prevPosY, posX, posY, sizeX, sizeY, motionX, motionY;
    private World world;

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
