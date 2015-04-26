package main.game.entity;

import main.game.render.ISprite;
import main.game.world.World;

public class EntityPlayer extends EntityLiving {

    public EntityPlayer(World world) {
        super(world);
        setSize(0.8, 1.6);
    }

    @Override
    public ISprite getSprite() {
        // TODO
        return null;
    }

}
