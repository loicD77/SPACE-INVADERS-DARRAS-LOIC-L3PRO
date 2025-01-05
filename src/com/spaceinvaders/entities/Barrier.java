package com.spaceinvaders.entities;

import static org.lwjgl.opengl.GL11.*;

public class Barrier {
    private float x, y;

    public Barrier(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render() {
        glBegin(GL_QUADS);
        glVertex2f(x - 0.1f, y - 0.05f);
        glVertex2f(x + 0.1f, y - 0.05f);
        glVertex2f(x + 0.1f, y + 0.05f);
        glVertex2f(x - 0.1f, y + 0.05f);
        glEnd();
    }
}
