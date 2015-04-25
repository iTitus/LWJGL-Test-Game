package main.game.entity;

import main.game.world.World;

public abstract class EntityLiving extends Entity {

    private int health, maxHealth;

    public EntityLiving(World world) {
        super(world);
    }

    public void attack(int amount) {
        setHealth(health - amount);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void heal(int amount) {
        setHealth(health + amount);
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(maxHealth, health));
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = Math.max(0, maxHealth);
    }

    public void setMaxHealthAndHealth(int maxHealth) {
        this.maxHealth = Math.max(0, maxHealth);
        setHealth(maxHealth);
    }

    @Override
    public void update() {
        super.update();
        if (health <= 0) {
            setDead();
        }
    }

}
