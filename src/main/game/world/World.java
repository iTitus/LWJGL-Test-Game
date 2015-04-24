package main.game.world;

import java.util.Random;

import main.game.tile.Tile;

public class World {

    private static final Random rand = new Random();
    private final int sizeX, sizeY;
    private final int[][] tiles;

    public World(int sizeX, int sizeY) {
        tiles = new int[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void genTestWorld() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                setTileAt(x, y, Math.random() >= 0.5 ? Tile.grass : Tile.stone);
            }
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Tile getTileAt(int x, int y) {
        return Tile.TILES[tiles[x][y]];
    }

    public void render() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                getTileAt(x, y).render(this, x, y);
            }
        }
    }

    public void setTileAt(int x, int y, Tile tile) {
        tiles[x][y] = tile != null ? tile.getTileID() : Tile.air.getTileID();
    }

    public void update() {
        setTileAt(rand.nextInt(sizeX), rand.nextInt(sizeY), Math.random() >= 0.5 ? Tile.grass : Tile.stone);
    }

}
