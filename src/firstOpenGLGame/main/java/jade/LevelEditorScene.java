package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.util.ShaderUtils;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelEditorScene extends Scene{
    private boolean changeScene = false;
    private float[] vertexArray = {
            //position             //color
             0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, //bottom right
            -0.5f,  0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, //top left
             0.5f,  0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, //top right
            -0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f //bottom left
    };
    private int[] elementArray = {
            2, 1, 0, //top right triangle
            0, 1, 3 //bottom left triangle
    };

    public LevelEditorScene() {}

    @Override
    public void init() {
        int programID = ShaderUtils.load(
                "src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");
    }

    @Override
    public void update(float dt) {
        if (!changeScene && Input.isKeyDown(GLFW_KEY_SPACE)) {
            System.out.println("hi");
            System.out.println("now in levelEditorScene");
            changeScene = true;
        } else if (changeScene) {
            Window.changeScene(1);
        }

    }


}
