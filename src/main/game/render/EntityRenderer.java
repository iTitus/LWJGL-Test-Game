package main.game.render;

import java.util.HashMap;
import java.util.Map;

import main.game.GameManger;
import main.game.entity.Entity;
import main.game.render.tile.TileRenderer;

import org.lwjgl.opengl.GL11;

public final class EntityRenderer {

    public static final String ENTITY_SHEET = "entitySheet";
    public static final int ENTITY_SHEET_SIZE = 256;
    private static final Map<Class<? extends Entity>, IRenderEntity<?>> entityRenderMap = new HashMap<Class<? extends Entity>, IRenderEntity<?>>();

    @SuppressWarnings("unchecked")
    public static <T extends Entity> IRenderEntity<T> getRenderEntity(Class<T> entityClass) {
        return (IRenderEntity<T>) entityRenderMap.get(entityClass);
    }

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

    public static void loadEntitySprites(ISpriteLoader loader) {
        for (IRenderEntity<?> renderEntity : entityRenderMap.values()) {
            renderEntity.loadSprites(loader);
        }
    }

    public static <T extends Entity> void registerRenderEntity(Class<T> entityClass, IRenderEntity<T> renderEntity) {
        if (entityClass == null || renderEntity == null) {
            throw new RuntimeException("Arguments must not be null");
        }
        if (entityRenderMap.containsKey(entityClass)) {
            throw new RuntimeException("RenderEntity already registered for entity " + entityClass);
        }
        entityRenderMap.put(entityClass, renderEntity);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Entity> void renderEntity(T entity) {
        ((IRenderEntity<T>) getRenderEntity(entity.getClass())).render(entity);
    }

    public static void renderStandardEntity(Entity e, ISprite sprite) {
        if (e != null && !e.isDead() && sprite != null) {
            double renderPosX = e.getPosX();
            double renderPosY = e.getPosY();
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
