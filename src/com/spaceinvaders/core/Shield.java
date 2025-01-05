package com.spaceinvaders.core;

import com.spaceinvaders.render.Renderer;

public class Shield {
    private float x, y, width, height;
    private int[][] pixels; // Représente les pixels du bouclier (0 = détruit, 1 = intact)

    public Shield(float x, float y, float width, float height, int rows, int cols) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pixels = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.pixels[i][j] = 1; // Initialise tous les pixels comme intact
            }
        }
    }

    public boolean isFullyDestroyed() {
        for (int[] row : pixels) {
            for (int pixel : row) {
                if (pixel == 1) return false; // Si au moins un pixel est intact, le bouclier n'est pas détruit
            }
        }
        return true; // Tous les pixels sont détruits
    }

    public void render(Renderer renderer) {
        float pixelWidth = width / pixels[0].length;
        float pixelHeight = height / pixels.length;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] == 1) {
                    float pixelX = x - width / 2 + j * pixelWidth + pixelWidth / 2;
                    float pixelY = y - height / 2 + i * pixelHeight + pixelHeight / 2;
                    renderer.drawRect(pixelX, pixelY, pixelWidth, pixelHeight, new float[]{0.0f, 1.0f, 0.0f});
                }
            }
        }
    }

    public boolean checkCollision(float bulletX, float bulletY) {
        float pixelWidth = width / pixels[0].length;
        float pixelHeight = height / pixels.length;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] == 1) {
                    float pixelX = x - width / 2 + j * pixelWidth;
                    float pixelY = y - height / 2 + i * pixelHeight;

                    if (bulletX >= pixelX && bulletX <= pixelX + pixelWidth &&
                            bulletY >= pixelY && bulletY <= pixelY + pixelHeight) {
                        pixels[i][j] = 0; // Détruit le pixel touché
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
