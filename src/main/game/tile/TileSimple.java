package main.game.tile;

import main.game.render.ISprite;
import main.game.render.ISpriteLoader;
import main.game.render.TileRenderer;

public class TileSimple extends Tile {

    private final int id, sheetPosition;
    private ISprite sprite;
    private final String tileName;

    public TileSimple(int id, String tileName, int sheetPosition) {
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
    public int getTileID() {
        return id;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    @Override
    public void loadSprites(ISpriteLoader loader) {
        sprite = loader.loadSprite(TileRenderer.TILE_SHEET, sheetPosition * TileRenderer.TILE_SIZE % TileRenderer.TILE_SHEET_SIZE, sheetPosition * TileRenderer.TILE_SIZE / TileRenderer.TILE_SHEET_SIZE, TileRenderer.TILE_SIZE, TileRenderer.TILE_SIZE);
    }

}
