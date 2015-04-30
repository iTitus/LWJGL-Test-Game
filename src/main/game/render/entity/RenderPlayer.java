package main.game.render.entity;

import main.game.entity.EntityPlayer;
import main.game.render.EntityRenderer;
import main.game.render.IRenderEntity;
import main.game.render.ISprite;
import main.game.render.ISpriteLoader;

public class RenderPlayer implements IRenderEntity<EntityPlayer> {

    private ISprite player_standing_still, player_walking_left[], player_walking_right[];

    @Override
    public void loadSprites(ISpriteLoader loader) {
        player_standing_still = loader.loadSprite("player", 0, 0, 32, 64);
        player_walking_left = new ISprite[1];
        for (int i = 0; i < player_walking_left.length; i++) {
            player_walking_left[i] = loader.loadSprite("player", 32 + i * 32, 64, 32, 64);
        }
        player_walking_right = new ISprite[1];
        for (int i = 0; i < player_walking_right.length; i++) {
            player_walking_right[i] = loader.loadSprite("player", 32 + i * 32, 0, 32, 64);
        }
    }

    @Override
    public void render(EntityPlayer entity) {
        EntityRenderer.renderStandardEntity(entity, player_standing_still);
    }

}
