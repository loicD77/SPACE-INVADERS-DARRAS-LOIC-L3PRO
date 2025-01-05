package com.spaceinvaders.render;

public class Glyph {
    private int id, x, y, width, height, xOffset, yOffset, xAdvance;
    private float u0, v0, u1, v1;

    public Glyph(String line) {
        String[] parts = line.split(" ");
        id = Integer.parseInt(parts[1].split("=")[1]);
        x = Integer.parseInt(parts[2].split("=")[1]);
        y = Integer.parseInt(parts[3].split("=")[1]);
        width = Integer.parseInt(parts[4].split("=")[1]);
        height = Integer.parseInt(parts[5].split("=")[1]);
        xOffset = Integer.parseInt(parts[6].split("=")[1]);
        yOffset = Integer.parseInt(parts[7].split("=")[1]);
        xAdvance = Integer.parseInt(parts[8].split("=")[1]);

        u0 = x / 512.0f;
        v0 = y / 512.0f;
        u1 = (x + width) / 512.0f;
        v1 = (y + height) / 512.0f;
    }

    public int getId() { return id; }
    public int getXOffset() { return xOffset; }
    public int getYOffset() { return yOffset; }
    public int getXAdvance() { return xAdvance; }
    public float getU0() { return u0; }
    public float getV0() { return v0; }
    public float getU1() { return u1; }
    public float getV1() { return v1; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
