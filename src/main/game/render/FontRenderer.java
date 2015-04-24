package main.game.render;

import org.lwjgl.opengl.GL11;

public class FontRenderer {

    private static int[] charCarriages;

    private static int charWidth, charHeight, charsPerLine;

    private static float charWidthUV, charHeightUV;
    private static ISprite fontTexture;

    public static void drawString(int x, int y, String str) {
        RenderManager.bindSprite(fontTexture);
        GL11.glColor3f(0.75f, 0, 0.75f);

        GL11.glBegin(GL11.GL_QUADS);
        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i);
            if (c < 0 || c > 255) {
                c = 0;
            }

            float u = c % charsPerLine * charWidthUV;
            float v = c / charsPerLine * charHeightUV;

            GL11.glTexCoord2f(u, v);
            GL11.glVertex3i(x + i * charCarriages[c], y, 0);

            GL11.glTexCoord2f(u, v + charHeightUV);
            GL11.glVertex3i(x + i * charCarriages[c], y - charHeight, 0);

            GL11.glTexCoord2f(u + charWidthUV, v + charHeightUV);
            GL11.glVertex3i(x + i * charCarriages[c] + charWidth, y - charHeight, 0);

            GL11.glTexCoord2f(u + charWidthUV, v);
            GL11.glVertex3i(x + i * charCarriages[c] + charWidth, y, 0);

        }

        GL11.glEnd();

        GL11.glColor3f(1, 1, 1);

    }

    public static void init(ISprite _fontTexture, int _charWidth, int _charHeight) {
        fontTexture = _fontTexture;
        charWidth = _charWidth;
        charHeight = _charHeight;

        charWidthUV = charWidth / 512F;
        charHeightUV = charHeight / 512F;

        charsPerLine = fontTexture.getTextureWidth() / charWidth;

        charCarriages = new int[256];
        for (int i = 0; i < charCarriages.length; i++) {
            if (i >= 0 && i <= 31 || i == 127 || i == 129 || i == 141 || i == 143 || i == 144 || i == 157) {
                charCarriages[i] = 19;
            } else {
                charCarriages[i] = 11;
            }

        }
    }

}
