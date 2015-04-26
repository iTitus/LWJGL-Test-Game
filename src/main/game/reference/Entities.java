package main.game.reference;

import java.util.HashMap;
import java.util.Map;

import main.game.entity.Entity;
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

    }

    public static void registerEntity(Class<? extends Entity> entityClass, String name) {
        if (entityClass == null || StringUtil.isNullOrEmpty(name)) {
            throw new NullPointerException("Unable to register entity: class and name must not be null or empty");
        }
        if (entityMappings.containsKey(entityClass) || entityBackMappings.containsKey(name)) {
            throw new RuntimeException("Entity already registered: " + entityClass + ", name " + name);
        }
        entityMappings.put(entityClass, name);
        entityBackMappings.put(name, entityClass);
    }

    private Entities() {
        // NO-OP
    }

}
