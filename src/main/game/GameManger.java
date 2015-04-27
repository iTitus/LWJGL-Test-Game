package main.game;

import main.game.entity.EntityPlayer;
import main.game.world.World;

public class GameManger {

    private static int offsetX, offsetY;
    private static EntityPlayer player;
    private static boolean running, isFullscreen, vsync;
    private static World world;

    public static int getOffsetX() {
        return offsetX;
    }

    public static int getOffsetY() {
        return offsetY;
    }

    public static EntityPlayer getPlayer() {
        return player;
    }

    public static World getWorld() {
        return world;
    }

    public static boolean isFullscreen() {
        return isFullscreen;
    }

    public static boolean isRunning() {
        return running;
    }

    public static boolean isVSyncEnabled() {
        return vsync;
    }

    public static void setFullscreen(boolean isFullscreen) {
        GameManger.isFullscreen = isFullscreen;
    }

    public static void setOffsetX(int offsetX) {
        GameManger.offsetX = offsetX;
    }

    public static void setOffsetY(int offsetY) {
        GameManger.offsetY = offsetY;
    }

    public static void setPlayer(EntityPlayer player) {
        GameManger.player = player;
    }

    public static void setRunning(boolean running) {
        GameManger.running = running;
    }

    public static void setVsyncEnabled(boolean vsync) {
        GameManger.vsync = vsync;
    }

    public static void setWorld(World world) {
        GameManger.world = world;
    }

}
