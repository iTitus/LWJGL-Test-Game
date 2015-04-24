package main.game.render.impl;

import main.game.render.ISprite;

public class Sprite implements ISprite {

    private final float minU, maxU, minV, maxV;
    private final int textureID, width, height;

    public Sprite(int textureID, int width, int height) {
        this.textureID = textureID;
        this.width = width;
        this.height = height;
        minU = 0;
        minV = 0;
        maxU = 1;
        maxV = 1;
    }

    public Sprite(int textureID, int width, int height, float minU, float minV, float maxU, float maxV) {
        this.textureID = textureID;
        this.width = width;
        this.height = height;
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }

    public Sprite(int textureID, int width, int height, int startX, int startY, int sizeX, float sizeY) {
        this.textureID = textureID;
        this.width = width;
        this.height = height;
        minU = (float) startX / width;
        minV = (float) startY / height;
        maxU = (float) (startX + sizeX) / width;
        maxV = (startY + sizeY) / height;
    }

    @Override
    public float getMaxU() {
        return maxU;
    }

    @Override
    public float getMaxV() {
        return maxV;
    }

    @Override
    public float getMinU() {
        return minU;
    }

    @Override
    public float getMinV() {
        return minV;
    }

    @Override
    public int getTextureHeight() {
        return height;
    }

    @Override
    public int getTextureID() {
        return textureID;
    }

    @Override
    public int getTextureWidth() {
        return width;
    }

}
