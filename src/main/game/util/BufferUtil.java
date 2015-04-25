package main.game.util;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;

public class BufferUtil {

    public static ByteBuffer getByteBuffer(BufferedImage image) {
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

    public static int makeBuffer(int target, FloatBuffer bufferdata) {
        IntBuffer bufferid = BufferUtils.createIntBuffer(1);
        GL15.glGenBuffers(bufferid);
        int buffer = bufferid.get(0);
        GL15.glBindBuffer(target, buffer);
        GL15.glBufferData(target, bufferdata, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(target, 0);
        return buffer;
    }

    public static int makeBuffer(int target, IntBuffer bufferdata) {
        IntBuffer bufferid = BufferUtils.createIntBuffer(1);
        GL15.glGenBuffers(bufferid);
        int buffer = bufferid.get(0);
        GL15.glBindBuffer(target, buffer);
        GL15.glBufferData(target, bufferdata, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(target, 0);
        return buffer;
    }

    public static FloatBuffer makeFloatBuffer(float[] ibuf) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(ibuf.length);
        for (float i : ibuf) {
            buf.put(i);
        }
        buf.flip();
        return buf;
    }

    public static FloatBuffer makeFloatBuffer(Matrix4f mat) {
        float[] f = { mat.m00, mat.m01, mat.m02, mat.m03, mat.m10, mat.m11, mat.m12, mat.m13, mat.m20, mat.m21, mat.m22, mat.m23, mat.m30, mat.m31, mat.m32, mat.m33 };
        return makeFloatBuffer(f);
    }

    public static IntBuffer makeIntBuffer(int[] ibuf) {
        IntBuffer buf = BufferUtils.createIntBuffer(ibuf.length);
        for (int i : ibuf) {
            buf.put(i);
        }
        buf.flip();
        return buf;
    }

}
