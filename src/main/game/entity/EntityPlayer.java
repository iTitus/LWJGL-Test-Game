package main.game.entity;

import main.game.GameManger;
import main.game.render.tile.TileRenderer;
import main.game.util.MathUtil;
import main.game.util.Vector2d;
import main.game.world.World;

import org.lwjgl.input.Keyboard;

public class EntityPlayer extends EntityLiving {

    private final double playerSpeed = 1D / TileRenderer.TILE_SIZE;

    public EntityPlayer(World world) {
        super(world);
        setSize(0.8, 1.6);
        setMaxHealthAndHealth(10);
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
