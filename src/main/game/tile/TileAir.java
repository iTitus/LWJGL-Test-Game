package main.game.tile;

import main.game.render.ISprite;

public class TileAir extends Tile {

    @Override
    public ISprite getSprite() {
        return null;
    }

    @Override
    public int getTileID() {
        return 0;
    }

    @Override
    public String getTileName() {
        return "air";
    }

}
