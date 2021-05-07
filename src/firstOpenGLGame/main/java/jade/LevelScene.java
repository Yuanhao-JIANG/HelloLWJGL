package firstOpenGLGame.main.java.jade;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelScene extends Scene{
    public LevelScene() {

    }

    @Override
    public void update(float dt) {
        if (Input.isKeyDown(GLFW_KEY_SPACE)) {
            System.out.println("now in levelScene");
            System.out.println("FPS:" + 1.0f/dt);
        }

    }


}
