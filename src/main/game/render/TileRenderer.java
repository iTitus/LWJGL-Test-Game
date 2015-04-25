package main.game.render;

import main.game.GameManger;
import main.game.tile.Tile;
import main.game.world.World;

import org.lwjgl.opengl.GL11;

public final class TileRenderer {

    private static final int[] INDICES = { 0, 1, 2, 2, 3, 0 };
    public static final String TILE_SHEET = "tileSheet";
    public static final int TILE_SHEET_SIZE = 256;
    public static final int TILE_SIZE = 32;
    private static final float[] VERTICES = { 0, 0, TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, 0, TILE_SIZE };

    private static void immediateDrawing(ISprite sprite) {
        GL11.glBegin(GL11.GL_TRIANGLES);
        {
            GL11.glTexCoord2f(sprite.getMinU(), sprite.getMinV());
            GL11.glVertex2f(0, TILE_SIZE);

            GL11.glTexCoord2f(sprite.getMaxU(), sprite.getMinV());
            GL11.glVertex2f(TILE_SIZE, TILE_SIZE);

            GL11.glTexCoord2f(sprite.getMaxU(), sprite.getMaxV());
            GL11.glVertex2f(TILE_SIZE, 0);

            GL11.glTexCoord2f(sprite.getMaxU(), sprite.getMaxV());
            GL11.glVertex2f(TILE_SIZE, 0);

            GL11.glTexCoord2f(sprite.getMinU(), sprite.getMaxV());
            GL11.glVertex2f(0, 0);

            GL11.glTexCoord2f(sprite.getMinU(), sprite.getMinV());
            GL11.glVertex2f(0, TILE_SIZE);
        }
        GL11.glEnd();
    }

    public static void renderStandardTile(World world, int x, int y, Tile tile) {

        ISprite sprite = tile.getSprite(world, x, y);

        if (sprite != null) {
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(x * TILE_SIZE + GameManger.getOffsetX(), y * TILE_SIZE + GameManger.getOffsetY(), 0);
                RenderManager.bindSprite(sprite);
                immediateDrawing(sprite);
            }
            GL11.glPopMatrix();
        }
    }
}
