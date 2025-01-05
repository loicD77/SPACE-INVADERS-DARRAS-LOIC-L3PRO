package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class Projectile {
    protected float x, y; // Modifié en `protected`
    protected float width, height;
    protected float speed;
    protected float[] color;

    public Projectile(float x, float y, float speed, float[] color) {
        this.x = x;
        this.y = y;
        this.width = 0.02f;
        this.height = 0.05f;
        this.speed = speed;
        this.color = color;
    }

    public void update(float delta) {
        y += speed * delta;
    }

    public void render(Renderer renderer) {
        renderer.drawRect(x, y, width, height, color);
    }

    public boolean isOffScreen() {
        return y > 1.0f || y < -1.0f;
    }

    // Ajout de collidesWith pour Alien
    public boolean collidesWith(Alien alien) {
        return x < alien.getX() + alien.getWidth() &&
                x + width > alien.getX() &&
                y < alien.getY() + alien.getHeight() &&
                y + height > alien.getY();
    }

    // Ajout de collidesWith pour Player
    public boolean collidesWith(Player player) {
        return x < player.getX() + player.getWidth() &&
                x + width > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + height > player.getY();
    }

    // Méthodes getter pour accès aux champs
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
