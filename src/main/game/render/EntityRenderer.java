package main.game.render;

import main.game.GameManger;
import main.game.MainTestGame;
import main.game.entity.Entity;

import org.lwjgl.opengl.GL11;

public final class EntityRenderer {

    public static final String ENTITY_SHEET = "entitySheet";
    public static final int ENTITY_SHEET_SIZE = 256;

    private static void immediateDrawing(ISprite sprite, double sizeX, double sizeY) {
        GL11.glBegin(GL11.GL_TRIANGLES);
        {
            GL11.glTexCoord2d(sprite.getMinU(), sprite.getMinV());
            GL11.glVertex2d(0, sizeY);

            GL11.glTexCoord2d(sprite.getMaxU(), sprite.getMinV());
            GL11.glVertex2d(sizeX, sizeY);

            GL11.glTexCoord2d(sprite.getMaxU(), sprite.getMaxV());
            GL11.glVertex2d(sizeX, 0);

            GL11.glTexCoord2d(sprite.getMaxU(), sprite.getMaxV());
            GL11.glVertex2d(sizeX, 0);

            GL11.glTexCoord2d(sprite.getMinU(), sprite.getMaxV());
            GL11.glVertex2d(0, 0);

            GL11.glTexCoord2d(sprite.getMinU(), sprite.getMinV());
            GL11.glVertex2d(0, sizeY);
        }
        GL11.glEnd();
    }

    public static void renderStandardEntity(Entity e) {
        if (e != null && !e.isDead()) {
            ISprite sprite = e.getSprite();
            if (sprite != null) {
                double renderPosX = e.getPrevPosX() + e.getMotionX() * MainTestGame.getDelta();
                double renderPosY = e.getPrevPosY() + e.getMotionY() * MainTestGame.getDelta();
                GL11.glPushMatrix();
                {
                    GL11.glTranslated(renderPosX * TileRenderer.TILE_SIZE + GameManger.getOffsetX(), renderPosY * TileRenderer.TILE_SIZE + GameManger.getOffsetY(), 0);
                    RenderManager.bindSprite(sprite);
                    immediateDrawing(sprite, e.getSizeX() * TileRenderer.TILE_SIZE, e.getSizeY() * TileRenderer.TILE_SIZE);
                }
                GL11.glPopMatrix();
            }
        }
    }
}
