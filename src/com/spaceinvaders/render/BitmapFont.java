package com.spaceinvaders.render;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class BitmapFont {
    private Texture fontTexture;
    private Map<Character, Glyph> glyphs;

    public BitmapFont(String fntPath, String texturePath) throws IOException {
        fontTexture = new Texture(texturePath);
        glyphs = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fntPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("char id")) {
                    Glyph glyph = new Glyph(line);
                    glyphs.put((char) glyph.getId(), glyph);
                }
            }
        }
    }

    public void drawText(String text, float x, float y, float scale) {
        glBindTexture(GL_TEXTURE_2D, fontTexture.getID());

        for (char c : text.toCharArray()) {
            Glyph glyph = glyphs.get(c);
            if (glyph != null) {
                drawGlyph(glyph, x, y, scale);
                x += glyph.getXAdvance() * scale;
            }
        }
    }

    private void drawGlyph(Glyph glyph, float x, float y, float scale) {
        float x0 = x + glyph.getXOffset() * scale;
        float y0 = y - glyph.getYOffset() * scale;
        float x1 = x0 + glyph.getWidth() * scale;
        float y1 = y0 - glyph.getHeight() * scale;

        float u0 = glyph.getU0();
        float v0 = glyph.getV0();
        float u1 = glyph.getU1();
        float v1 = glyph.getV1();

        glBegin(GL_QUADS);
        glTexCoord2f(u0, v0); glVertex2f(x0, y0);
        glTexCoord2f(u1, v0); glVertex2f(x1, y0);
        glTexCoord2f(u1, v1); glVertex2f(x1, y1);
        glTexCoord2f(u0, v1); glVertex2f(x0, y1);
        glEnd();
    }
}
