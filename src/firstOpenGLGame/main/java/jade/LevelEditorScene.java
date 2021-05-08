package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.util.ShaderUtils;

import static firstOpenGLGame.main.java.util.ShaderUtils.uploadFloat;
import static firstOpenGLGame.main.java.util.ShaderUtils.uploadMatrix4f;
import static firstOpenGLGame.main.java.util.VAOUtil.createVAO;
import static firstOpenGLGame.main.java.util.VAOUtil.draw;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class LevelEditorScene extends Scene{
    private boolean changeScene = false;
    private final float[] vertexArray = {
            //position             //color
            100.0f,   0.0f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, //bottom right
              0.0f,   100f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f, //top left
            100.0f, 100.0f, 0.0f,     1.0f, 0.0f, 1.0f, 1.0f, //top right
              0.0f,   0.0f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f //bottom left
    };
    private final int[] elementArray = {
            2, 1, 0, //top right triangle
            0, 1, 3 //bottom left triangle
    };
    int shaderProgram;
    int vao;
    Camera camera;

    public LevelEditorScene() {}

    @Override
    public void init() {
        shaderProgram = ShaderUtils.load(
                "src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");
        vao = createVAO(vertexArray, elementArray);
        camera = new Camera();
    }

    @Override
    public void update(float dt) {
        camera.camPos.x -= dt * 50.0f;
        camera.camPos.y -= dt * 50.0f;
        camera.camPos.add(camera.camFront, camera.camTarget);
        glUseProgram(shaderProgram);
        uploadMatrix4f(shaderProgram, "uProjection",
                camera.getOrthoProjectionMatrix());
        uploadMatrix4f(shaderProgram, "uView",
                camera.getViewMatrix());
        uploadFloat(shaderProgram, "uTime", (float) glfwGetTime());
        glBindVertexArray(vao);
        draw(6);

        glUseProgram(0);
        glBindVertexArray(0);
    }


}
