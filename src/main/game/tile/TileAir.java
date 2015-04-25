package main.game.tile;

import main.game.render.ISprite;

public class TileAir extends Tile {

    public TileAir() {
        setSolid(false);
    }

    @Override
    public ISprite getSprite() {
        return null;
    }

    @Override
    public byte getTileID() {
        return 0;
    }

    @Override
    public String getTileName() {
        return "air";
    }

}
