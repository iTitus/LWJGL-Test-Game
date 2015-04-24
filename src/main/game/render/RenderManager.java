package main.game.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public final class RenderManager {

    private static int currentTextureID;

    public static void bindSprite(ISprite sprite) {
        if (sprite != null) {
            if (sprite.getTextureID() != currentTextureID) {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getTextureID());
                currentTextureID = sprite.getTextureID();
            }
        } else {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
    }

    public static void delete(ISprite sprite) {
        if (sprite != null) {
            GL11.glDeleteTextures(sprite.getTextureID());
        }
    }

    public static int getNewProgramID() {
        return GL20.glCreateProgram();
    }

    public static int getNewTextureID() {
        return GL11.glGenTextures();
    }

    public static int getNewVertexBufferObjectID() {
        return GL15.glGenBuffers();
    }

    public static void releaseShaderProgram() {
        useShaderProgram(null);
    }

    public static void unbindSprite() {
        bindSprite(null);
    }

    public static void useShaderProgram(IShader shader) {
        if (shader != null) {
            GL20.glUseProgram(shader.getProgramID());
        } else {
            GL20.glUseProgram(0);
        }
    }

    private RenderManager() {
    }
}
