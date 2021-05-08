package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.util.ShaderUtils;

import static firstOpenGLGame.main.java.util.VAOUtil.createVAO;
import static firstOpenGLGame.main.java.util.VAOUtil.draw;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class LevelEditorScene extends Scene{
    private boolean changeScene = false;
    private final float[] vertexArray = {
            //position             //color
             0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, //bottom right
            -0.5f,  0.5f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f, //top left
             0.5f,  0.5f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f, //top right
            -0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f //bottom left
    };
    private final int[] elementArray = {
            2, 1, 0, //top right triangle
            0, 1, 3 //bottom left triangle
    };
    int program;
    int vao;

    public LevelEditorScene() {}

    @Override
    public void init() {
        program = ShaderUtils.load(
                "src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");
        vao = createVAO(vertexArray, elementArray);
    }

    @Override
    public void update(float dt) {
        glUseProgram(program);
        glBindVertexArray(vao);
        draw(6);

        glUseProgram(0);
        glBindVertexArray(0);
    }


}
