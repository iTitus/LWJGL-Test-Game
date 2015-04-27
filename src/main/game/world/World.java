package main.game.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import main.game.entity.Entity;
import main.game.tile.Tile;
import main.game.util.IEntityMatcher;

public class World {

    public static final int MAX_TILE_META = 1 << 24;
    private final List<Entity> entities, spawnList;
    @SuppressWarnings("unused")
    private final Random rand = new Random();
    private final int sizeX, sizeY;
    private final int[][] tiles;

    public World(int sizeX, int sizeY) {
        tiles = new int[sizeX][sizeY];
        entities = new ArrayList<Entity>();
        spawnList = new ArrayList<Entity>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void genTestWorld() {
        for (int x = 0; x < sizeX; x++) {
            setTileAt(x, sizeY - 1, Tile.stone);
            setTileAt(x, 0, Tile.grass);
        }
        for (int y = 0; y < sizeY; y++) {
            setTileAt(sizeX - 1, y, Tile.stone);
            setTileAt(0, y, Tile.stone);
        }
    }

    public List<Entity> getEntitiesAt(double posX, double posY, double sizeX, double sizeY) {
        List<Entity> list = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e != null && e.isInside(posX, posY, sizeX, sizeY)) {
                list.add(e);
            }
        }
        return list;
    }

    public List<Entity> getEntitiesExcludingEntityAt(double posX, double posY, double sizeX, double sizeY, Entity entityToExclude) {
        List<Entity> list = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e != null && e != entityToExclude && e.isInside(posX, posY, sizeX, sizeY)) {
                list.add(e);
            }
        }
        return list;
    }

    public <T extends Entity> List<T> getEntitiesOfClassAt(double posX, double posY, double sizeX, double sizeY, Class<T> entityClass) {
        List<T> list = new ArrayList<T>();
        for (Entity e : entities) {
            if (e != null && entityClass.isInstance(e) && e.isInside(posX, posY, sizeX, sizeY)) {
                list.add(entityClass.cast(e));
            }
        }
        return list;
    }

    public <T extends Entity> List<T> getEntitiesOfClassExcludingEntityAt(double posX, double posY, double sizeX, double sizeY, Entity entityToExclude, Class<T> entityClass) {
        List<T> list = new ArrayList<T>();
        for (Entity e : entities) {
            if (e != null && e != entityToExclude && entityClass.isInstance(e) && e.isInside(posX, posY, sizeX, sizeY)) {
                list.add(entityClass.cast(e));
            }
        }
        return list;
    }

    public <T extends Entity> List<T> getEntitiesOfClassWithMatcherAt(double posX, double posY, double sizeX, double sizeY, IEntityMatcher matcher, Class<T> entityClass) {
        List<T> list = new ArrayList<T>();
        for (Entity e : entities) {
            if (e != null && entityClass.isInstance(e) && e.isInside(posX, posY, sizeX, sizeY) && matcher.matches(e)) {
                list.add(entityClass.cast(e));
            }
        }
        return list;
    }

    public <T extends Entity> List<T> getEntitiesOfClassWithMatcherExcludingEntityAt(double posX, double posY, double sizeX, double sizeY, Entity entityToExclude, IEntityMatcher matcher, Class<T> entityClass) {
        List<T> list = new ArrayList<T>();
        for (Entity e : entities) {
            if (e != null && e != entityToExclude && entityClass.isInstance(e) && e.isInside(posX, posY, sizeX, sizeY) && matcher.matches(e)) {
                list.add(entityClass.cast(e));
            }
        }
        return list;
    }

    public List<Entity> getEntitiesWithMatcherAt(double posX, double posY, double sizeX, double sizeY, IEntityMatcher matcher) {
        List<Entity> list = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e != null && e.isInside(posX, posY, sizeX, sizeY) && matcher.matches(e)) {
                list.add(e);
            }
        }
        return list;
    }

    public List<Entity> getEntitiesWithMatcherExcludingEntityAt(double posX, double posY, double sizeX, double sizeY, Entity entityToExclude, IEntityMatcher matcher) {
        List<Entity> list = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e != null && e != entityToExclude && e.isInside(posX, posY, sizeX, sizeY) && matcher.matches(e)) {
                list.add(e);
            }
        }
        return list;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Tile getTileAt(int x, int y) {
        return Tile.TILES[tiles[x][y] & 0xFF];
    }

    public int getTileMetaAt(int x, int y) {
        return tiles[x][y] >>> 8;
    }

    public void render() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                getTileAt(x, y).render(this, x, y);
            }
        }
        for (Entity e : entities) {
            if (e != null && !e.isDead()) {
                e.render();
            }
        }
    }

    public void setTileAt(int x, int y, Tile tile) {
        setTileAt(x, y, tile, 0);
    }

    public void setTileAt(int x, int y, Tile tile, int meta) {
        if (meta < 0 || meta >= MAX_TILE_META) {
            throw new RuntimeException("Meta " + meta + " is out of bounds");
        }
        tiles[x][y] = tile.getTileID() & 0xFF | meta << 8;
    }

    public void setTileMetaAt(int x, int y, int meta) {
        if (meta < 0 || meta >= MAX_TILE_META) {
            throw new RuntimeException("Meta " + meta + " is out of bounds");
        }
        tiles[x][y] = tiles[x][y] & 0xFF | meta << 8;
    }

    public void spawnEntity(Entity e) {
        if (e != null && !spawnList.contains(e) && !entities.contains(e)) {
            spawnList.add(e);
        }
    }

    public void update() {
        // setTileAt(rand.nextInt(sizeX), rand.nextInt(sizeY), Math.random() >= 0.5 ? Tile.grass : Tile.stone);
        if (!spawnList.isEmpty()) {
            entities.addAll(spawnList);
            spawnList.clear();
        }
        for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
            Entity e = iterator.next();
            if (e != null) {
                e.update();
                if (e.isDead()) {
                    iterator.remove();
                }
            }
        }
    }
}
