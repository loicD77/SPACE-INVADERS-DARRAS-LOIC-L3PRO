package com.spaceinvaders.entities;

import com.spaceinvaders.render.Renderer;

public class MysteryShip {
    private float x, y;
    private float width, height;
    private float speed;
    private boolean active;

    public MysteryShip(float x, float y, float width, float height, float speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.active = false; // Désactivé au départ
    }

    public void spawn(float spawnX, float directionSpeed) {
        this.x = spawnX;
        this.speed = directionSpeed;
        this.active = true; // Activer le vaisseau
    }

    public void update(float delta) {
        if (!active) return;

        x += speed * delta;

        // Désactiver le vaisseau lorsqu'il sort de l'écran
        if ((speed > 0 && x > 1.2f) || (speed < 0 && x < -1.2f)) {
            deactivate();
        }
    }

    public void render(Renderer renderer) {
        if (!active) return;

        // Dessiner le rectangle rouge (Mystery Ship)
        renderer.drawRect(x, y, width, height, new float[]{1.0f, 0.0f, 0.0f});
    }

    // Méthode pour vérifier les collisions avec un projectile
    public boolean checkCollision(float bulletX, float bulletY) {
        if (!active) return false;

        return bulletX < x + width &&
                bulletX > x &&
                bulletY < y + height &&
                bulletY > y;
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }
}
