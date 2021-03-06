package main.game.tile;

import main.game.render.ISprite;
import main.game.render.ISpriteLoader;
import main.game.render.tile.TileRenderer;

public class TileSimple extends Tile {

    private final byte id;
    private final int sheetPosition;
    private ISprite sprite;
    private final String tileName;

    public TileSimple(byte id, String tileName, int sheetPosition) {
        this.id = id;
        this.tileName = tileName;
        this.sheetPosition = sheetPosition;
    }

    public int getSheetPosition() {
        return sheetPosition;
    }

    @Override
    public ISprite getSprite() {
        return sprite;
    }

    @Override
    public byte getTileID() {
        return id;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    @Override
    public void loadSprites(ISpriteLoader loader) {
        sprite = loader.loadSprite(TileRenderer.TILE_SHEET, sheetPosition % TileRenderer.TILES_IN_A_ROW * TileRenderer.TILE_SIZE, sheetPosition / TileRenderer.TILES_IN_A_ROW * TileRenderer.TILE_SIZE, TileRenderer.TILE_SIZE, TileRenderer.TILE_SIZE);
    }

}
