package com.spaceinvaders.entities;

import static org.lwjgl.opengl.GL11.*;

public abstract class Entity {
    protected float x, y, z;

    public Entity(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public abstract void update();

    public abstract void render();
}
