package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.components.FontRenderer;
import firstOpenGLGame.main.java.components.SpriteRenderer;
import firstOpenGLGame.main.java.util.ShaderUtils;

import static firstOpenGLGame.main.java.util.ShaderUtils.*;
import static firstOpenGLGame.main.java.util.TextureUtil.genTextureID;
import static firstOpenGLGame.main.java.util.VAOUtil.createVAO;
import static firstOpenGLGame.main.java.util.VAOUtil.draw;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class LevelEditorScene extends Scene{
    //private boolean changeScene = false;
    private final float[] vertexArray = {
            //position               //color                    //texture cord
            100.0f,   0.0f, 0.0f,    1.0f, 0.0f, 0.0f, 1.0f,    1, 0, //bottom right
              0.0f,   100f, 0.0f,    0.0f, 1.0f, 0.0f, 1.0f,    0, 1, //top left
            100.0f, 100.0f, 0.0f,    1.0f, 0.0f, 1.0f, 1.0f,    1, 1, //top right
              0.0f,   0.0f, 0.0f,    1.0f, 1.0f, 0.0f, 1.0f,    0, 0  //bottom left
    };
    private final int[] elementArray = {
            2, 1, 0, //top right triangle
            0, 1, 3 //bottom left triangle
    };
    int shaderProgram;
    int vao;
    Camera camera;
    int testTexture;
    GameObject testObj;

    public LevelEditorScene() {}

    @Override
    public void init() {
        System.out.println("Creating test object.");
        testObj = new GameObject("test Object");
        testObj.addComponent(new SpriteRenderer());
        testObj.addComponent(new FontRenderer());
        addGameObjectToScene(testObj);
        shaderProgram = ShaderUtils.load(
                "src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");
        vao = createVAO(vertexArray, elementArray);
        camera = new Camera();
        testTexture = genTextureID("src/firstOpenGLGame/assets/images/testImage.png");

        setTextUnit(shaderProgram, "tex", 0);
        bindTexture(GL_TEXTURE0, testTexture);
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

        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }
    }


}
