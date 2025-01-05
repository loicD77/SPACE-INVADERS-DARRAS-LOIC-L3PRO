package com.spaceinvaders.render;

import static org.lwjgl.opengl.GL11.*;
import java.io.IOException;

public class Renderer {
    private Texture fontTexture;

    public Renderer() {
        try {
            fontTexture = new Texture("res/fonts/arial.png"); // Charge la texture de la police Arial
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la texture de police.");
        }
    }

    public void drawBitmapText(String text, float x, float y, float size, float[] color) {
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, fontTexture.getID());
        glColor3f(color[0], color[1], color[2]);

        float cursorX = x;
        for (char c : text.toCharArray()) {
            drawCharacter(c, cursorX, y, size);
            cursorX += size * 0.6f; // Ajuste l'espacement entre les caractères
        }

        glDisable(GL_TEXTURE_2D);
    }

    private void drawCharacter(char c, float x, float y, float size) {
        float texSize = 1.0f / 16.0f; // Taille d'un caractère dans la texture (grille 16x16)
        int asciiIndex = c - 32;     // Décalage ASCII, commence à 32

        if (asciiIndex < 0 || asciiIndex >= 256) {
            return; // Ignore les caractères non mappés
        }

        float u = (asciiIndex % 16) * texSize; // Coordonnée U
        float v = (asciiIndex / 16) * texSize; // Coordonnée V

        glBegin(GL_QUADS);
        glTexCoord2f(u, v);
        glVertex2f(x, y);

        glTexCoord2f(u + texSize, v);
        glVertex2f(x + size, y);

        glTexCoord2f(u + texSize, v + texSize);
        glVertex2f(x + size, y + size);

        glTexCoord2f(u, v + texSize);
        glVertex2f(x, y + size);
        glEnd();
    }

    public void drawRect(float x, float y, float width, float height, float[] color) {
        glDisable(GL_TEXTURE_2D); // Assurez-vous que la texture est désactivée pour les formes simples
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        glVertex2f(x - width / 2, y - height / 2);
        glVertex2f(x + width / 2, y - height / 2);
        glVertex2f(x + width / 2, y + height / 2);
        glVertex2f(x - width / 2, y + height / 2);
        glEnd();
    }

    public void drawGround(float y, float[] color) {
        glDisable(GL_TEXTURE_2D); // Assurez-vous que la texture est désactivée pour les formes simples
        glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        glVertex2f(-1.0f, y);
        glVertex2f(1.0f, y);
        glVertex2f(1.0f, y + 0.05f);
        glVertex2f(-1.0f, y + 0.05f);
        glEnd();
    }
}
