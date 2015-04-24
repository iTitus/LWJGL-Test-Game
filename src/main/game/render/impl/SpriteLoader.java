package main.game.render.impl;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.game.render.ISprite;
import main.game.render.ISpriteLoader;
import main.game.render.RenderManager;
import main.game.util.FileUtils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public final class SpriteLoader implements ISpriteLoader {

    private static class ImageData {

        private final int textureID, width, height;

        public ImageData(int textureID, int width, int height) {
            this.textureID = textureID;
            this.width = width;
            this.height = height;
        }
    }

    private static ISpriteLoader instance;

    private static final Map<String, ImageData> loadedImages = new HashMap<String, ImageData>();

    private static final List<ISprite> sprites = new ArrayList<ISprite>();

    public static ISpriteLoader getInstance() {
        if (instance == null) {
            instance = new SpriteLoader();
        }
        return instance;
    }

    private SpriteLoader() {
    }

    private ByteBuffer getByteBuffer(BufferedImage image) {
        if (image == null) {
            return null;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) (pixel >> 16 & 0xFF));
                buffer.put((byte) (pixel >> 8 & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) (pixel >> 24 & 0xFF));
            }
        }

        buffer.flip();
        return buffer;
    }

    @Override
    public List<ISprite> getRegisteredSprites() {
        return sprites;
    }

    @Override
    public ISprite loadSprite(String path) {
        ImageData data = loadedImages.get(path);
        if (data != null) {
            ISprite sprite = new Sprite(data.textureID, data.width, data.height);
            sprites.add(sprite);
            return sprite;
        } else {
            BufferedImage image = FileUtils.loadImage(path);
            ByteBuffer buffer = getByteBuffer(image);
            int textureID = RenderManager.getNewTextureID();
            setupImage(textureID, buffer, image.getWidth(), image.getHeight());
            loadedImages.put(path, new ImageData(textureID, image.getWidth(), image.getHeight()));
            ISprite sprite = new Sprite(textureID, image.getWidth(), image.getHeight());
            sprites.add(sprite);
            return sprite;
        }
    }

    @Override
    public ISprite loadSprite(String path, float minU, float minV, float maxU, float maxV) {
        ImageData data = loadedImages.get(path);
        if (data != null) {
            ISprite sprite = new Sprite(data.textureID, data.width, data.height, minU, minV, maxU, maxV);
            sprites.add(sprite);
            return sprite;
        } else {
            BufferedImage image = FileUtils.loadImage(path);
            ByteBuffer buffer = getByteBuffer(image);
            int textureID = RenderManager.getNewTextureID();
            setupImage(textureID, buffer, image.getWidth(), image.getHeight());
            loadedImages.put(path, new ImageData(textureID, image.getWidth(), image.getHeight()));
            ISprite sprite = new Sprite(textureID, image.getWidth(), image.getHeight(), minU, minV, maxU, maxV);
            sprites.add(sprite);
            return sprite;
        }
    }

    @Override
    public ISprite loadSprite(String path, int startX, int startY, int sizeX, int sizeY) {
        System.out.println("#######################");
        System.out.println(startX + " | " + startY + " | " + sizeX + " | " + sizeY);
        ImageData data = loadedImages.get(path);
        if (data != null) {
            System.out.println(path + " is already loaded!");
            ISprite sprite = new Sprite(data.textureID, data.width, data.height, startX, startY, sizeX, sizeY);
            sprites.add(sprite);
            return sprite;
        } else {
            System.out.println(path + " is NOT already loaded!");
            BufferedImage image = FileUtils.loadImage(path);
            ByteBuffer buffer = getByteBuffer(image);
            int textureID = RenderManager.getNewTextureID();
            setupImage(textureID, buffer, image.getWidth(), image.getHeight());
            loadedImages.put(path, new ImageData(textureID, image.getWidth(), image.getHeight()));
            ISprite sprite = new Sprite(textureID, image.getWidth(), image.getHeight(), startX, startY, sizeX, sizeY);
            sprites.add(sprite);
            return sprite;
        }
    }

    private void setupImage(int textureID, ByteBuffer buffer, int width, int height) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

    }
}
