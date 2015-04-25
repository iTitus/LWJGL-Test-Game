package main.game.render;

import main.game.GameManger;
import main.game.tile.Tile;
import main.game.world.World;

import org.lwjgl.opengl.GL11;

public final class TileRenderer {

    @SuppressWarnings("unused")
    private static final int[] INDICES = { 0, 1, 2, 2, 3, 0 };
    public static final String TILE_SHEET = "tileSheet";
    public static final int TILE_SIZE = 32;
    public static final int TILES_IN_A_ROW = 8;
    @SuppressWarnings("unused")
    private static final double[] VERTICES = { 0, 0, TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, 0, TILE_SIZE };

    private static void immediateDrawing(ISprite sprite) {
        GL11.glBegin(GL11.GL_TRIANGLES);
        {
            GL11.glTexCoord2d(sprite.getMinU(), sprite.getMinV());
            GL11.glVertex2d(0, TILE_SIZE);

            GL11.glTexCoord2d(sprite.getMaxU(), sprite.getMinV());
            GL11.glVertex2d(TILE_SIZE, TILE_SIZE);

            GL11.glTexCoord2d(sprite.getMaxU(), sprite.getMaxV());
            GL11.glVertex2d(TILE_SIZE, 0);

            GL11.glTexCoord2d(sprite.getMaxU(), sprite.getMaxV());
            GL11.glVertex2d(TILE_SIZE, 0);

            GL11.glTexCoord2d(sprite.getMinU(), sprite.getMaxV());
            GL11.glVertex2d(0, 0);

            GL11.glTexCoord2d(sprite.getMinU(), sprite.getMinV());
            GL11.glVertex2d(0, TILE_SIZE);
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
