package learnOpenGL.main;

import learnOpenGL.window.Window;

public class Main {
    private Window window;

    private void init() {
        window = new Window(800, 600, "learnOpenGL", 72.0f/255.0f, 140.0f/255.0f, 190.0f/255.0f, 1.0f);
        window.create();
    }

    private void update() {
        window.update();
    }
    private void render() {
        window.swapBuffer();
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
        }
        window.terminate();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
