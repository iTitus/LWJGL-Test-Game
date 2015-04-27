package main.game.entity;

import main.game.GameManger;
import main.game.MainTestGame;
import main.game.render.ISprite;
import main.game.render.ISpriteLoader;
import main.game.render.TileRenderer;
import main.game.world.World;

import org.lwjgl.input.Keyboard;

public class EntityPlayer extends EntityLiving {

    public static final double PLAYER_SPEED = 1D / TileRenderer.TILE_SIZE;

    private static ISprite player_standing_still, player_walking_left[], player_walking_right[];

    public static void loadSprites(ISpriteLoader loader) {
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

    public EntityPlayer(World world) {
        super(world);
        setSize(0.8, 1.6);
        setMaxHealthAndHealth(10);
    }

    @Override
    public ISprite getSprite() {
        if (motionX < 0) {
            return player_walking_left[MainTestGame.getTicks() % player_walking_left.length];
        } else if (motionX > 0) {
            return player_walking_right[MainTestGame.getTicks() % player_walking_right.length];
        }
        return player_standing_still;
    }

    @Override
    public void update() {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            GameManger.setOffsetY(GameManger.getOffsetY() - 1);
            motionY = PLAYER_SPEED;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            GameManger.setOffsetX(GameManger.getOffsetX() + 1);
            motionX = -PLAYER_SPEED;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            GameManger.setOffsetY(GameManger.getOffsetY() + 1);
            motionY = -PLAYER_SPEED;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            GameManger.setOffsetX(GameManger.getOffsetX() - 1);
            motionX = PLAYER_SPEED;
        }
        super.update();
    }

}
