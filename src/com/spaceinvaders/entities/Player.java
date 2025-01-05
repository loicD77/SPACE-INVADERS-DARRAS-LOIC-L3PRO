package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Player {
    private float x, y;
    private float width, height;
    private float[] color;

    public Player(float x, float y, float width, float height, float[] color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void update(boolean moveLeft, boolean moveRight, float delta) {
        float speed = 0.5f;
        if (moveLeft) x -= speed * delta;
        if (moveRight) x += speed * delta;

        x = Math.max(-1.0f + width / 2, Math.min(1.0f - width / 2, x));
    }

    public void render(Renderer renderer) {
        renderer.drawRect(x, y, width, height, color);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
