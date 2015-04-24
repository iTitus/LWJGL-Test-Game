package main.game.render;

import java.util.List;

public interface ISpriteLoader {

    List<ISprite> getRegisteredSprites();

    ISprite loadSprite(String path);

    ISprite loadSprite(String path, float minU, float minV, float maxU, float maxV);

    ISprite loadSprite(String path, int startX, int startY, int sizeX, int sizeY);

}
