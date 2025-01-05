package com.spaceinvaders.entities;

public class CurvedProjectile extends Projectile {
    private float curveStrength;

    public CurvedProjectile(float x, float y, float speed, float curveStrength, float[] color) {
        super(x, y, speed, color);
        this.curveStrength = curveStrength;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        x += curveStrength * delta; // Accès corrigé
    }
}
