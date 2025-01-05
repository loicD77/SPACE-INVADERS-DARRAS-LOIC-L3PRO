package com.spaceinvaders.entities;

public class ExplodingProjectile extends Projectile {
    private boolean exploded = false;

    public ExplodingProjectile(float x, float y, float speed, float[] color) {
        super(x, y, speed, color);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!exploded && y < -0.8f) { // Accès à `y` est maintenant possible
            exploded = true;
            // Ajouter logique d'explosion ici
        }
    }

    public boolean hasExploded() {
        return exploded;
    }
}
