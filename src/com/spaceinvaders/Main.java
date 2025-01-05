package com.spaceinvaders;

import com.spaceinvaders.core.Game;
import com.spaceinvaders.render.Renderer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private long window;
    private Renderer renderer;
    private Game game;

    public void run() {
        init();
        loop();

        glfwTerminate();
    }

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        window = glfwCreateWindow(800, 600, "Space Invaders", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - 800) / 2, (vidMode.height() - 600) / 2);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        renderer = new Renderer();
        game = new Game();


        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_LEFT) {
                game.setMoveLeft(action != GLFW_RELEASE);
            }
            if (key == GLFW_KEY_RIGHT) {
                game.setMoveRight(action != GLFW_RELEASE);
            }
            if (key == GLFW_KEY_SPACE && action == GLFW_PRESS) {
                game.setShooting(true);
            }
        });
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            game.update(0.016f); // Simulation de 60 FPS
            game.render(renderer);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
