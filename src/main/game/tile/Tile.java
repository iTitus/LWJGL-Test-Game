package main.game.tile;

import main.game.render.ISprite;
import main.game.render.ISpriteLoader;
import main.game.render.TileRenderer;
import main.game.render.impl.SpriteLoader;
import main.game.world.World;

public abstract class Tile {

    public static Tile air, grass, stone;

    public static final int SIZE = 16;
    public static final Tile[] TILES = new Tile[256];

    public static void init() {

        air = registerTile(new TileAir());
        stone = registerTile(new TileSimple(1, "stone", 0));
        grass = registerTile(new TileSimple(2, "grass", 1));

        for (Tile tile : TILES) {
            if (tile != null) {
                tile.loadSprites(SpriteLoader.getInstance());
            }
        }
    }

    public static Tile registerTile(Tile tile) {
        if (TILES[tile.getTileID()] != null) {
            throw new RuntimeException("ID already occupied");
        }
        TILES[tile.getTileID()] = tile;
        return tile;
    }

    public abstract ISprite getSprite();

    public ISprite getSprite(World world, int x, int y) {
        return getSprite();
    }

    public abstract int getTileID();

    public abstract String getTileName();

    public void loadSprites(ISpriteLoader loader) {
    }

    public void render(World world, int x, int y) {
        TileRenderer.renderStandardTile(world, x, y, this);
    }
}
