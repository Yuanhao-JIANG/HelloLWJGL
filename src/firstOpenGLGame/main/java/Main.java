package firstOpenGLGame.main.java;

import firstOpenGLGame.main.java.jade.Window;

public class Main {
    public static void main(String[] args) {
        Window window = Window.get(960, 540, "Mario", 1.0f, 1.0f, 1.0f, 1.0f);
        window.run();
    }
}
