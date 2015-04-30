package main.game.render.entity;

import main.game.entity.EntityMovingStone;
import main.game.render.EntityRenderer;
import main.game.render.IRenderEntity;
import main.game.render.ISprite;
import main.game.render.ISpriteLoader;

public class RenderMovingStone implements IRenderEntity<EntityMovingStone> {

    private ISprite sprite;

    @Override
    public void loadSprites(ISpriteLoader loader) {
        sprite = loader.loadSprite("icon");
    }

    @Override
    public void render(EntityMovingStone entity) {
        EntityRenderer.renderStandardEntity(entity, sprite);
    }
}
