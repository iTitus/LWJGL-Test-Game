package main.game.render;

import main.game.entity.Entity;

public interface IRenderEntity<T extends Entity> {

    void loadSprites(ISpriteLoader loader);

    void render(T entity);

}
