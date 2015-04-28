package main.game.entity;

import main.game.GameManger;
import main.game.render.ISprite;
import main.game.render.ISpriteLoader;
import main.game.render.TileRenderer;
import main.game.util.MathUtil;
import main.game.util.Vector2d;
import main.game.world.World;

import org.lwjgl.input.Keyboard;

public class EntityPlayer extends EntityLiving {

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

    private final double playerSpeed = 1D / TileRenderer.TILE_SIZE;

    public EntityPlayer(World world) {
        super(world);
        setSize(0.8, 1.6);
        setMaxHealthAndHealth(10);
    }

    @Override
    public ISprite getSprite() {
        // if (motionX < 0) {
        // return player_walking_left[MainTestGame.getTicks() % player_walking_left.length];
        // } else if (motionX > 0) {
        // return player_walking_right[MainTestGame.getTicks() % player_walking_right.length];
        // }
        return player_standing_still;
    }

    @Override
    public void update() {

        // if (Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
        // Vector2d v = tryMove(0, PLAYER_SPEED);
        // GameManger.setOffsetY(MathUtil.floor(GameManger.getOffsetY() - (v.getY() * TileRenderer.TILE_SIZE)));
        // } else if (Keyboard.isKeyDown(Keyboard.KEY_S) && !Keyboard.isKeyDown(Keyboard.KEY_W)) {
        // Vector2d v = tryMove(0, -PLAYER_SPEED);
        // GameManger.setOffsetY(MathUtil.floor(GameManger.getOffsetY() - (v.getY() * TileRenderer.TILE_SIZE)));
        // }

        if (Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
            Vector2d v = tryMove(-playerSpeed, 0);
            GameManger.setOffsetX(MathUtil.floor(GameManger.getOffsetX() - v.getX() * TileRenderer.TILE_SIZE));
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A)) {
            Vector2d v = tryMove(playerSpeed, 0);
            GameManger.setOffsetX(MathUtil.floor(GameManger.getOffsetX() - v.getX() * TileRenderer.TILE_SIZE));
        }
        super.update();
    }

}
