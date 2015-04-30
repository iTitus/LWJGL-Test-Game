package main.game.entity;

import main.game.util.Vector2d;
import main.game.world.World;

public class EntityMovingStone extends Entity {

    private boolean falling = false;
    private double moved;
    private double moveDistance;
    private final double speed = 1D / 8;

    public EntityMovingStone(World world) {
        super(world);
        setSize(1, 1);
    }

    public EntityMovingStone(World world, double moveDistance) {
        this(world);
        this.moveDistance = moveDistance;
    }

    @Override
    public void update() {
        if (falling) {
            Vector2d v = tryMove(0, world.getGravity(this));
            moved += v.getY();
        } else {
            Vector2d v = tryMove(0, speed);
            moved += v.getY();
        }
        if (moved >= moveDistance) {
            falling = true;
        } else if (moved <= 0) {
            falling = false;
        }
    }
}
