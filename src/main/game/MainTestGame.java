package main.game;

import javax.imageio.ImageIO;

import main.game.reference.GameLib;
import main.game.render.FontRenderer;
import main.game.render.ISprite;
import main.game.render.RenderManager;
import main.game.render.impl.SpriteLoader;
import main.game.tile.Tile;
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
    private static int fps;
    private static int ticks;

    private static void createDisplay() throws LWJGLException {
        ImageIO.setUseCache(false);
        Display.setFullscreen(GameManger.isFullscreen());
        Display.setDisplayMode(getPreferredDisplayMode());
        Display.setTitle(GameLib.TITLE);
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

    private static void drawFPS() {
        FontRenderer.drawString(1, GameLib.HEIGHT + 3, "FPS: " + fps);
    }

    private static void end() {
        Display.destroy();
    }

    public static double getDelta() {
        return delta;
    }

    public static int getFPS() {
        return fps;
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

    private static void handleInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                GameManger.setRunning(false);
            }
        }

        if (Display.isCloseRequested()) {
            GameManger.setRunning(false);
        }

        while (Mouse.next()) {
            if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
                System.out.println(Mouse.getEventX() + " - " + Mouse.getEventY());
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

        drawFPS();

        int error = GL11.glGetError();
        if (error != GL11.GL_NO_ERROR) {
            throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(error));
        }
    }

    private static void run() throws LWJGLException {
        createDisplay();
        setup();
        start();
        end();
    }

    private static void setup() {
        GameManger.setRunning(true);
        GameManger.setFullscreen(false);
        GameManger.setVsyncEnabled(true);

        Tile.init();

        GameManger.setWorld(new World(64, 64));

        // GameManger.getWorld().genTestWorld();
        GameManger.getWorld().genDebugWorld();

    }

    private static void start() {

        long now = 0L;
        int frameCounter = 0;
        long lastTime = getTime();
        long lastFPSMeasure = lastTime;

        while (GameManger.isRunning()) {
            now = getTime();
            delta += (now - lastTime) / (1000 / GameLib.TPS);
            lastTime = now;
            while (delta >= 1) {
                handleInput();
                update();
                ticks++;
                delta--;
            }
            render();
            Display.update();

            if (getTime() - lastFPSMeasure > 1000) {
                fps = frameCounter;
                frameCounter = 0;
                lastFPSMeasure += 1000;
            }
            frameCounter++;

        }

        stop();

    }

    private static void stop() {
        for (ISprite sprite : SpriteLoader.getInstance().getRegisteredSprites()) {
            RenderManager.delete(sprite);
        }
        Display.destroy();
    }

    private static void update() {
        GameManger.getWorld().update();
    }

}