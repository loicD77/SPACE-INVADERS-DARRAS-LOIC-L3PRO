package com.spaceinvaders.entities;

public class ZigzagProjectile extends Projectile {
    private float zigzagSpeed;

    public ZigzagProjectile(float x, float y, float speed, float zigzagSpeed, float[] color) {
        super(x, y, speed, color);
        this.zigzagSpeed = zigzagSpeed;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        x += Math.sin(y * 10) * zigzagSpeed * delta; // Accès corrigé
    }
}
