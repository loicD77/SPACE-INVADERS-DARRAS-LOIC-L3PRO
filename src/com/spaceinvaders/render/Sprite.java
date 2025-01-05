package com.spaceinvaders.render;

import org.lwjgl.opengl.GL11;

public class Sprite {
    private String imagePath;

    public Sprite(String imagePath) {
        this.imagePath = imagePath;
        // Charger l'image et la transformer en texture OpenGL
    }

    public void draw(float x, float y) {
        // Code pour dessiner une texture avec OpenGL
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + 0.1f, y);
        GL11.glVertex2f(x + 0.1f, y + 0.1f);
        GL11.glVertex2f(x, y + 0.1f);
        GL11.glEnd();
    }
}
