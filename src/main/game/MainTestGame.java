package main.game;

import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import main.game.reference.GameLib;
import main.game.render.FontRenderer;
import main.game.render.ISprite;
import main.game.render.RenderManager;
import main.game.render.impl.SpriteLoader;
import main.game.tile.Tile;
import main.game.util.BufferUtil;
import main.game.util.FileUtil;
import main.game.world.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class MainTestGame {

    private static double delta;
    private static int fps, tps, ticks;

    private static void createDisplay() throws LWJGLException {
        ImageIO.setUseCache(false);
        Display.setFullscreen(GameManger.isFullscreen());
        Display.setDisplayMode(getPreferredDisplayMode());
        Display.setTitle(GameLib.TITLE);
        Display.setIcon(getIconArray());
        Display.setVSyncEnabled(GameManger.isVSyncEnabled());
        Display.setResizable(false);
        Display.create();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, GameLib.WIDTH, 0, GameLib.HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        FontRenderer.init(SpriteLoader.getInstance().loadSprite("font"), 32, 32);

    }

    public static double getDelta() {
        return delta;
    }

    public static int getFPS() {
        return fps;
    }

    private static ByteBuffer[] getIconArray() {
        return new ByteBuffer[] { BufferUtil.getByteBuffer(FileUtil.loadImage("icon")) };
    }

    private static DisplayMode getPreferredDisplayMode() throws LWJGLException {
        DisplayMode[] displayModes = Display.getAvailableDisplayModes();
        for (DisplayMode displayMode : displayModes) {
            if (displayMode != null && displayMode.getWidth() == GameLib.WIDTH && displayMode.getHeight() == GameLib.HEIGHT && displayMode.getBitsPerPixel() == GameLib.BPP) {
                return displayMode;
            }
        }
        throw new UnsupportedOperationException("Display not supported");
    }

    public static int getTicks() {
        return ticks;
    }

    private static long getTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static int getTPS() {
        return tps;
    }

    private static void handleInput() throws LWJGLException {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                GameManger.setRunning(false);
            }
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_F11) {
                GameManger.setFullscreen(!GameManger.isFullscreen());
                Display.setFullscreen(GameManger.isFullscreen());
                System.out.println("Switching to " + (GameManger.isFullscreen() ? "fullscreen" : "windowed") + " mode");
            }

            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_F12) {
                GameManger.setVsyncEnabled(!GameManger.isVSyncEnabled());
                Display.setVSyncEnabled(GameManger.isVSyncEnabled());
                System.out.println((GameManger.isVSyncEnabled() ? "Enabling" : "Disabling") + " VSync");
            }
        }

        if (Display.isCloseRequested()) {
            GameManger.setRunning(false);
        }

        while (Mouse.next()) {
            if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
                System.out.println(Mouse.getEventX() - GameManger.getOffsetX() + " - " + (Mouse.getEventY() - GameManger.getOffsetY()));
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            GameManger.setOffsetY(GameManger.getOffsetY() - 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            GameManger.setOffsetX(GameManger.getOffsetX() + 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            GameManger.setOffsetY(GameManger.getOffsetY() + 1);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            GameManger.setOffsetX(GameManger.getOffsetX() - 1);
        }

    }

    public static void main(String[] args) {
        try {
            run();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    private static void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GameManger.getWorld().render();

        FontRenderer.drawString(1, GameLib.HEIGHT + 3, "FPS: " + fps + " | TPS: " + tps);

        int error = GL11.glGetError();
        if (error != GL11.GL_NO_ERROR) {
            throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(error));
        }
    }

    private static void run() throws LWJGLException {
        System.out.println("Initialising startup routine");
        createDisplay();
        setup();
        start();
        stop();
    }

    private static void setup() {
        GameManger.setRunning(true);
        GameManger.setFullscreen(false);
        GameManger.setVsyncEnabled(true);

        Tile.init();

        GameManger.setWorld(new World(64, 64));

        GameManger.getWorld().genTestWorld();

    }

    private static void start() throws LWJGLException {

        System.out.println("Successfully started " + GameLib.TITLE);

        long now = 0L;
        int frameCounter = 0;
        int tickCounter = 0;
        long lastTime = getTime();
        long lastFPSMeasure = lastTime;
        long lastTPSMeasure = lastTime;

        while (GameManger.isRunning()) {
            now = getTime();
            delta += (now - lastTime) / (1000 / GameLib.TPS);
            lastTime = now;
            while (delta >= 1) {
                handleInput();
                update();
                ticks++;
                delta--;
                tickCounter++;
            }
            render();
            Display.update();
            frameCounter++;

            if (getTime() - lastFPSMeasure > 1000) {
                fps = frameCounter;
                frameCounter = 0;
                lastFPSMeasure += 1000;
            }

            if (getTime() - lastTPSMeasure > 1000) {
                tps = tickCounter;
                tickCounter = 0;
                lastTPSMeasure += 1000;
            }

        }

    }

    private static void stop() {
        System.out.println("Initialising shutdown routine");
        for (ISprite sprite : SpriteLoader.getInstance().getRegisteredSprites()) {
            RenderManager.delete(sprite);
        }
        Display.destroy();
        System.out.println("Shutting down...");
    }

    private static void update() {
        GameManger.getWorld().update();
    }

}
