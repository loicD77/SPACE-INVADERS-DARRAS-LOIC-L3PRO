package com.spaceinvaders.render;

import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        width = image.getWidth();
        height = image.getHeight();
        int[] pixelsRaw = new int[width * height];
        image.getRGB(0, 0, width, height, pixelsRaw, 0, width);

        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

        for (int i = 0; i < width * height; i++) {
            int pixel = pixelsRaw[i];
            pixels.put((byte) ((pixel >> 16) & 0xFF)); // Red
            pixels.put((byte) ((pixel >> 8) & 0xFF));  // Green
            pixels.put((byte) (pixel & 0xFF));         // Blue
            pixels.put((byte) ((pixel >> 24) & 0xFF)); // Alpha
        }

        pixels.flip();

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
    }

    public int getID() {
        return id;
    }

    public void cleanup() {
        glDeleteTextures(id);
    }
}
