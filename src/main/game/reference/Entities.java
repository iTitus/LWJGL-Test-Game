package main.game.reference;

import java.util.HashMap;
import java.util.Map;

import main.game.entity.Entity;
import main.game.entity.EntityMovingStone;
import main.game.entity.EntityPlayer;
import main.game.render.EntityRenderer;
import main.game.render.IRenderEntity;
import main.game.render.entity.RenderMovingStone;
import main.game.render.entity.RenderPlayer;
import main.game.util.ReflectionUtil;
import main.game.util.StringUtil;
import main.game.world.World;

public class Entities {

    private static final Map<String, Class<? extends Entity>> entityBackMappings = new HashMap<String, Class<? extends Entity>>();
    private static final Map<Class<? extends Entity>, String> entityMappings = new HashMap<Class<? extends Entity>, String>();

    public static Entity createEntityByName(String name, World world) {
        return ReflectionUtil.newInstance(getEntityClass(name), world);
    }

    public static Class<? extends Entity> getEntityClass(String name) {
        return entityBackMappings.get(name);
    }

    public static String getEntityName(Class<? extends Entity> entityClass) {
        return entityMappings.get(entityClass);
    }

    public static void init() {
        registerEntity(EntityPlayer.class, "player", new RenderPlayer());
        registerEntity(EntityMovingStone.class, "movingStone", new RenderMovingStone());
    }

    public static boolean isEntityRegistered(Class<? extends Entity> entityClass) {
        return entityMappings.containsKey(entityClass);
    }

    public static <T extends Entity> void registerEntity(Class<T> entityClass, String name, IRenderEntity<T> renderEntity) {
        if (entityClass == null || StringUtil.isNullOrEmpty(name) || renderEntity == null) {
            throw new NullPointerException("Unable to register entity: class name and render must not be null or empty");
        }
        if (entityMappings.containsKey(entityClass) || entityBackMappings.containsKey(name)) {
            throw new RuntimeException("Entity already registered: " + entityClass + ", name " + name);
        }
        entityMappings.put(entityClass, name);
        entityBackMappings.put(name, entityClass);
        EntityRenderer.registerRenderEntity(entityClass, renderEntity);
    }

    private Entities() {
        // NO-OP
    }

}
