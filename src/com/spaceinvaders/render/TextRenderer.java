package com.spaceinvaders.render;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class TextRenderer {
    private String fontPath;

    public TextRenderer(String fontPath) {
        this.fontPath = fontPath;
        // Chargez la police ou configurez les ressources ici
    }

    public void renderText(String text, float x, float y) {
        // Ajoutez le rendu du texte ici
        System.out.println("Rendering text: " + text + " at position (" + x + ", " + y + ")");
    }
}
