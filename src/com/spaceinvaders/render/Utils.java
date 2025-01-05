package com.spaceinvaders.render;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Utils {
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream(resource);
        if (is == null) {
            throw new IOException("Resource not found: " + resource);
        }

        byte[] buffer = is.readAllBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buffer.length);
        byteBuffer.put(buffer);
        byteBuffer.flip();
        return byteBuffer;
    }
}

