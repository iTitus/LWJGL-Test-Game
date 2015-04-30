package main.game.tile;

import main.game.entity.Entity;
import main.game.render.ISprite;
import main.game.render.ISpriteLoader;
import main.game.render.tile.TileRenderer;
import main.game.world.World;

public abstract class Tile {

    public static Tile air, grass, stone;

    public static final int SIZE = 16;
    public static final Tile[] TILES = new Tile[Byte.MAX_VALUE];

    public static void init() {
        air = registerTile(new TileAir());
        stone = registerTile(new TileSimple((byte) 1, "stone", 0));
        grass = registerTile(new TileSimple((byte) 2, "grass", 1));
    }

    public static void loadTileSprites(ISpriteLoader loader) {
        for (Tile tile : TILES) {
            if (tile != null) {
                tile.loadSprites(loader);
            }
        }
    }

    public static Tile registerTile(Tile tile) {
        if (TILES[tile.getTileID()] != null) {
            throw new RuntimeException("ID already occupied");
        }
        if (tile.getTileID() < 0 || tile.getTileID() >= TILES.length) {
            throw new RuntimeException("ID out of bounds");
        }
        TILES[tile.getTileID()] = tile;
        return tile;
    }

    private boolean solid = true;

    public abstract ISprite getSprite();

    public ISprite getSprite(World world, int x, int y) {
        return getSprite();
    }

    public abstract byte getTileID();

    public abstract String getTileName();

    public boolean isSolid() {
        return solid;
    }

    public void loadSprites(ISpriteLoader loader) {
        // NO-OP
    }

    public void onWalkOn(World world, int x, int y, Entity entity) {
        // NO-OP
    }

    public void render(World world, int x, int y) {
        TileRenderer.renderStandardTile(world, x, y, this);
    }

    public Tile setSolid(boolean solid) {
        this.solid = solid;
        return this;
    }
}
