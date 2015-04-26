package main.game.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;

import main.game.MainTestGame;

public class FileUtil {

    public static BufferedImage loadImage(String name) {
        try {
            BufferedImage image = ImageIO.read(MainTestGame.class.getResource("resources" + File.separator + "images" + File.separator + name + ".png"));
            Logger.info("Successfully loaded image: '" + name + ".png'");
            return image;
        } catch (Exception e) {
            throw new RuntimeException("Unable to load image '" + name + "'", e);
        }
    }

    public static String readShaderFileAsString(String name) {
        File shaderFile = null;
        try {
            URL url = MainTestGame.class.getResource("resources" + File.separator + "shaders" + File.separator + name);
            URI uri = url.toURI();
            shaderFile = new File(uri);
        } catch (Exception e) {
            throw new RuntimeException("Unable to locate shader file '" + name + "'", e);
        }
        String file = "";
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(shaderFile))) {
            while ((line = reader.readLine()) != null) {
                file += line + '\n';
            }
            Logger.info("Successfully loaded shader '" + name + "'");
        } catch (Exception e) {
            throw new RuntimeException("Unable to load shader from file '" + name + "'", e);
        }
        return file;
    }
}
