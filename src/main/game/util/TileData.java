package main.game.util;

import main.game.tile.Tile;

public class TileData {

    private final Tile tile;
    public final int x, y;

    public TileData(Tile tile, int x, int y) {
        super();
        this.tile = tile;
        this.x = x;
        this.y = y;
    }

    public Tile getTile() {
        return tile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
