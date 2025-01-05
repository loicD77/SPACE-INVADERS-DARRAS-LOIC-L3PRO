package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Bullet {
    private float x, y;
    private float prevX, prevY; // Position précédente pour la détection de trajectoire
    private float width, height;
    private float speed;
    private float[] color;

    public Bullet(float x, float y, float speed, float[] color) {
        this.x = x;
        this.y = y;
        this.prevX = x; // Initialiser la position précédente
        this.prevY = y;
        this.width = 0.02f;
        this.height = 0.05f;
        this.speed = speed;
        this.color = color;
    }

    public void update(float delta) {
        prevX = x; // Met à jour la position précédente avant de déplacer
        prevY = y;
        y += speed * delta;
    }

    public void render(Renderer renderer) {
        renderer.drawRect(x, y, width, height, color);
    }

    public boolean isOffScreen() {
        return y > 1.0f || y < -1.0f;
    }

    public boolean collidesWith(Alien alien) {
        return x < alien.getX() + alien.getWidth() &&
                x + width > alien.getX() &&
                y < alien.getY() + alien.getHeight() &&
                y + height > alien.getY();
    }

    public boolean collidesWith(Player player) {
        return x < player.getX() + player.getWidth() &&
                x + width > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + height > player.getY();
    }

    public void changeColor(float[] newColor) {
        this.color = newColor;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getPrevX() {
        return prevX;
    }

    public float getPrevY() {
        return prevY;
    }
}
