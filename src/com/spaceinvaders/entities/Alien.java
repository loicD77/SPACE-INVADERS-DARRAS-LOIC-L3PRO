package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Alien {
    private float x, y;
    private float width, height;
    private float[] color;
    private int score; // Nouveau champ pour le score

    // Constructeur modifié pour inclure le score
    public Alien(float x, float y, float width, float height, float[] color, int score) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.score = score;
    }

    public void update(float delta, boolean movingRight) {
        float horizontalSpeed = 0.2f;
        x += (movingRight ? 1 : -1) * horizontalSpeed * delta;
    }

    public void render(Renderer renderer) {
        renderer.drawRect(x, y, width, height, color);
    }

    public void descend(float amount) {
        y -= amount;
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

    public float[] getColor() {
        return color;
    }

    public int getScore() { // Getter pour le score
        return score;
    }
}
