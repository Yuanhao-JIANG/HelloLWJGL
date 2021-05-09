package firstOpenGLGame.main.java.jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

import static firstOpenGLGame.main.java.jade.Input.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final int width, height;
    private final String title;
    private final float clearColorR, clearColorG, clearColorB, clearColorA;
    private static Window window = null;
    private long glfwWindow;
    private static Scene currentScene;

    private Window(int width, int height, String title, float r, float g, float b, float a) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.clearColorR = r;
        this.clearColorG = g;
        this.clearColorB = b;
        this.clearColorA = a;
    }

    public static Window get(int width, int height, String title, float r, float g, float b, float a) {
        if (Window.window == null) {
            Window.window = new Window(width, height, title, r, g, b, a);
        }

        return Window.window;
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false: "Unknown scene" + newScene + ".";
                break;
        }
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW.");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // set to invisible until window is fully created
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) throw new IllegalStateException("Fail to crate GLFW window.");


        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(glfwWindow); // set to visible after window is fully created
        createCapabilities(); // must have
        setCallbacks();

        changeScene(0);
    }

    private void setCallbacks() {
        GLFW.glfwSetKeyCallback(glfwWindow, getKeyCallback());
        GLFW.glfwSetMouseButtonCallback(glfwWindow, getMouseButtonCallback());
        GLFW.glfwSetCursorPosCallback(glfwWindow, getCursorPosCallback());
        GLFW.glfwSetScrollCallback(glfwWindow, getScrollCallback());
    }

    public void loop() {
        float currentTime;
        float lastTime = (float) glfwGetTime();
        float deltaTime;
        while (!glfwWindowShouldClose(glfwWindow)) {
            currentTime = (float) glfwGetTime(); // in seconds
            deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            glfwPollEvents();
            glClearColor(clearColorR, clearColorG, clearColorB, clearColorA);
            glClear(GL_COLOR_BUFFER_BIT);
            currentScene.update(deltaTime);

            glfwSwapBuffers(glfwWindow);
        }
    }
}
