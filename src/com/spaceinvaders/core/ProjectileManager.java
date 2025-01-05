package com.spaceinvaders.core;

import com.spaceinvaders.entities.Projectile;
import com.spaceinvaders.render.Renderer;

import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {
    private List<Projectile> projectiles;

    public ProjectileManager() {
        projectiles = new ArrayList<>();

        // Couleurs pour les projectiles
        float[][] projectileColors = {
                {1.0f, 0.0f, 0.0f}, // Rouge
                {0.0f, 1.0f, 0.0f}, // Vert
                {0.0f, 0.0f, 1.0f}, // Bleu
                {1.0f, 1.0f, 0.0f}, // Jaune
                {1.0f, 0.0f, 1.0f}  // Magenta
        };

        // Exemple d'ajout de projectiles avec différentes couleurs
        for (int i = 0; i < projectileColors.length; i++) {
            projectiles.add(new Projectile(-0.5f + i * 0.3f, -0.5f, 0.1f, projectileColors[i]));
        }
    }

    public void update(float delta) {
        for (Projectile projectile : projectiles) {
            projectile.update(delta);
        }
    }

    public void render(Renderer renderer) {
        for (Projectile projectile : projectiles) {
            projectile.render(renderer);
        }
    }
}
