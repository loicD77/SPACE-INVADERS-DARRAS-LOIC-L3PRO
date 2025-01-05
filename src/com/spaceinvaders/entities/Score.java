package com.spaceinvaders.entities;

import static org.lwjgl.opengl.GL11.*;

public class Score {
    private int currentScore;
    private int hiScore;
    private int lives;

    public Score() {
        this.currentScore = 0;
        this.hiScore = 0;
        this.lives = 3;
    }

    public void increment(int points) {
        currentScore += points;
        if (currentScore > hiScore) {
            hiScore = currentScore;
        }
    }

    public void loseLife() {
        lives--;
    }

    public void render() {
        glColor3f(1.0f, 1.0f, 1.0f);
        // Vous pouvez ajouter un rendu textuel pour afficher le score
        // Ajoutez ici le rendu avec TextRenderer ou un équivalent
    }

    public boolean isGameOver() {
        return lives <= 0;
    }
}
