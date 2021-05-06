package learnOpenGL.window;

import learnOpenGL.graphics.Shader;
import learnOpenGL.graphics.Texture;
import learnOpenGL.graphics.VertexArray;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static learnOpenGL.graphics.Data.*;
import static learnOpenGL.graphics.Texture.bind;
import static learnOpenGL.graphics.Texture.upload;
import static learnOpenGL.graphics.VertexArray.draw;
import static learnOpenGL.math.VectorUtil.*;
import static learnOpenGL.math.VelocityUtil.getVelocity;
import static learnOpenGL.window.Input.getMouseX;
import static learnOpenGL.window.Input.getMouseY;
import static org.joml.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;


public class Window {
    private final int width, height;
    private final String title;
    private long window;
    private final float bgR, bgG, bgB, bgA;

    float[] arc = setArc(0, 0, 2.0f, 30.0d, 180.0d, 10);
    int[] arcIndices = setArcIndices(10);

    //mouse control
    boolean mouseInitiated = false;
    float lastX, lastY;
    float xOffset, yOffset;
    float scrollYOffset;
    float scrollYOffsetStore, fovStore;
    float mouseSensitivity = 0.1f, scrollSensitivity = 0.7f;
    float yaw = -90.0f, pitch;
    Vector3f direction = new Vector3f();

    float groundPosHeight = -1.3f;
    float jumpSpeed = 3.0f;
    float currentSpeed = jumpSpeed;
    float g = -10.0f;
    boolean isJumping = false;
    Vector3f cameraPos = new Vector3f(0.0f, groundPosHeight, 3.0f);
    Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    Vector3f cameraTarget = new Vector3f();
    Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
    Matrix4f model = new Matrix4f();
    Matrix4f floorModel = new Matrix4f();
    Matrix4f view = new Matrix4f();
    Matrix4f projection = new Matrix4f();
    float fov = 45.0f;
    float[] modelArray = new float[16];
    float[] floorModelArray = new float[16];
    float[] viewArray = new float[16];
    float[] projectionArray = new float[16];
    float speedIndicator, cameraSpeed;

    private VertexArray va;
    private VertexArray floorVA;
    private VertexArray circleVA;
    private Shader shader;
    private Shader floorShader;

    float deltaTime = 0.0f;	// time between current frame and last frame
    float lastFrame = 0.0f;

    public Window(int width, int height, String title, float r, float g, float b, float a) {
        this.width = width;
        this.height = height;
        this.title = title;
        bgR = r;
        bgG = g;
        bgB = b;
        bgA = a;
    }

    public void swapBuffer() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.out.println("GLFW wasn't initialized");
        }

        /*MacOS uses Legacy Profile as default for all created OpenGL context. Therefore by default only OpenGL up to
        2.1 and GLSL up to 1.20 is supported. To use OpenGL 3.2+ you need to switch to the Core Profile.
        the following 4 lines of codes do the work so that we can use our shader.*/
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
            return;
        }
        GLFW.glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // default is 1
        GL.createCapabilities(); //must have

        GL11.glViewport(0, 0, width, height);

        //set the position of the window
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        assert vidMode != null;
        int windowPosX = (vidMode.width() - width) / 2;
        int windowPosY = (vidMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX, windowPosY);

        glEnable(GL_DEPTH_TEST);
        //set and crate vertex arrays
        va = new VertexArray(getCube(), getCubeIndices());
        floorVA = new VertexArray(getFloor(), getFloorIndices());
        circleVA = new VertexArray(arc, arcIndices);
        va.create();
        floorVA.create();
        circleVA.create();
        //set shaders
        shader = new Shader("src/learnOpenGL/shader/cubesVertex.glsl", "src/learnOpenGL/shader/cubesFragment.glsl");
        floorShader = new Shader("src/learnOpenGL/shader/cubesVertex.glsl", "src/learnOpenGL/shader/floorFragment.glsl");
        //set textures
        Texture texture1 = new Texture("src/learnOpenGL/texture/container.jpg");
        Texture texture2 = new Texture("src/learnOpenGL/texture/awesomeFace.png");
        Texture texture3 = new Texture("src/learnOpenGL/texture/scenery.jpeg");
        //set texture units and bind textures with the expected unit
        shader.loadProgram();
        upload(shader, "myTexture1", 0);
        upload(shader, "myTexture2", 1);
        upload(shader, "myTexture3", 2);
        floorShader.loadProgram();
        upload(floorShader, "floorTexture", 3);
        bind(GL_TEXTURE0, texture1);
        bind(GL_TEXTURE1, texture2);
        bind(GL_TEXTURE2, texture3);
        bind(GL_TEXTURE3, texture3);

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        setCallbacks();

        GLFW.glfwShowWindow(window);
    }

    private void setCallbacks() {
        GLFW.glfwSetKeyCallback(window, Input.getKeyCallback());
        GLFW.glfwSetMouseButtonCallback(window, Input.getMouseButtonCallback());
        GLFW.glfwSetCursorPosCallback(window, Input.getCursorPosCallback());
        GLFW.glfwSetScrollCallback(window, Input.getScrollCallback());
    }

    public void update(){
        float currentFrame = (float) glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;
        processInput(window);

        GL11.glClearColor(bgR, bgG, bgB, bgA);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //set up transformation matrix
        cameraTarget.set(addTwo(cameraPos, cameraFront));
        // the up vector of lookAt matrix only affect the roll value, so the z value of up doesn't matter
        view.setLookAt(cameraPos, cameraTarget, up);
        view.get(viewArray);
        projection.setPerspective(toRadians(fov), (float) width / (float) height, 0.1f, 100.0f);
        projection.get(projectionArray);

        //cubes
        GL30.glBindVertexArray(va.getVao());
        shader.loadProgram();
        glUniformMatrix4fv(glGetUniformLocation(shader.getShaderProgram(), "view"), false, viewArray);
        glUniformMatrix4fv(glGetUniformLocation(shader.getShaderProgram(), "projection"), false, projectionArray);
        for (int i = 0; i < 10; i++) {
            model.translation(getCubePositions()[i]);
            Vector3f rotateAxis = new Vector3f();
            if (i == 0) {
                rotateAxis.add(new Vector3f(1.0f, 1.0f, 0.0f).normalize());
            } else {
                getCubePositions()[i].normalize(rotateAxis);
            }
            model.rotate((float) glfwGetTime() * toRadians(20.0f * (i + 1.0f)), rotateAxis.normalize());
            model.get(modelArray);
            glUniformMatrix4fv(glGetUniformLocation(shader.getShaderProgram(), "model"), false, modelArray);
            draw(36);
        }

        //floor
        floorShader.loadProgram();
        floorModel.translation(getFloorPosition());
        floorModel.scale(10);
        floorModel.get(floorModelArray);
        glUniformMatrix4fv(glGetUniformLocation(floorShader.getShaderProgram(), "model"), false, floorModelArray);
        glUniformMatrix4fv(glGetUniformLocation(floorShader.getShaderProgram(), "view"), false, viewArray);
        glUniformMatrix4fv(glGetUniformLocation(floorShader.getShaderProgram(), "projection"), false, projectionArray);
        GL30.glBindVertexArray(floorVA.getVao());
        draw(6);

        //circle
        shader.loadProgram();
        model.translation(0,0,0);
        model.get(modelArray);
        glUniformMatrix4fv(glGetUniformLocation(shader.getShaderProgram(), "model"), false, modelArray);
        GL30.glBindVertexArray(circleVA.getVao());
        draw(arc.length/8);

        GLFW.glfwPollEvents();
    }

    public void terminate() {
        GLFW.glfwTerminate();
    }

    private void processInput(long window) {
        //keyboard operation
        if (Input.isKeyDown(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window, true);
        }

        Vector3f moveFront = new Vector3f(cameraFront.x, cameraFront.y, cameraFront.z);
        moveFront.setComponent(1, 0.0f);
        if (Input.isKeyDown(GLFW_KEY_LEFT_CONTROL)) {
            speedIndicator = 5.0f;
        } else {
            speedIndicator = 1.5f;
        }
        cameraSpeed = speedIndicator * deltaTime;
        //move front
        if (Input.isKeyDown(GLFW_KEY_W)) {
            cameraPos.add(scalar(moveFront, cameraSpeed));
        }
        //move back
        if (Input.isKeyDown(GLFW_KEY_S)) {
            cameraPos.sub(scalar(moveFront, cameraSpeed));
        }
        //move left
        if (Input.isKeyDown(GLFW_KEY_A)) {
            cameraPos.sub(scalar(crossTwo(moveFront, cameraUp).normalize(), cameraSpeed));
        }
        //move right
        if (Input.isKeyDown(GLFW_KEY_D)) {
            cameraPos.add(scalar(crossTwo(moveFront, cameraUp).normalize(), cameraSpeed));
        }
        //move down/up
        /*if (Input.isKeyDown(GLFW_KEY_LEFT_SHIFT) && Input.isKeyDown(GLFW_KEY_SPACE)) {
            cameraPos.sub(scalar(cameraUp, cameraSpeed));
        } else */if (Input.isKeyDown(GLFW_KEY_SPACE) && cameraPos.y == groundPosHeight) {
            isJumping = true;
        }
        if (isJumping) {
            currentSpeed = getVelocity(currentSpeed, g, deltaTime);
            cameraPos.add(scalar(cameraUp, currentSpeed * deltaTime));
            if (cameraPos.y <= groundPosHeight) {
                isJumping = false;
                cameraPos.setComponent(1, groundPosHeight);
                currentSpeed = jumpSpeed;
            }
        }

        //mouse moving
        if (!mouseInitiated && (getMouseX() != 0|| getMouseY() != 0)) {
            lastX = getMouseX();
            lastY = getMouseY();
            mouseInitiated = true;
        }
        xOffset = getMouseX() - lastX;
        yOffset = lastY - getMouseY();
        lastX = getMouseX();
        lastY = getMouseY();

        xOffset *= mouseSensitivity;
        yOffset *= mouseSensitivity;

        yaw   += xOffset;
        pitch += yOffset;
        if (yaw > 360.0f) yaw -= 360.0f;
        if (yaw < -360.0f) yaw += 360.0f;
        if (pitch > 89.0f) pitch = 89.0f;
        if (pitch < -89.0f) pitch = -89.0f;

        direction.setComponent(0, cos(toRadians(yaw)) * cos(toRadians(pitch)));
        direction.setComponent(1, sin(toRadians(pitch)));
        direction.setComponent(2, sin(toRadians(yaw)) * cos(toRadians(pitch)));
        cameraFront.set(direction);

        //zooming
        scrollYOffset = Input.getScrollYOffset();
        if (scrollYOffset != 0) {
            scrollYOffsetStore = scrollYOffset;
            fovStore = fov;
        }
        float scale = scrollYOffsetStore * scrollSensitivity;
        if (scrollYOffsetStore < 0 && fov - 0.005 * scale < fovStore - scale) fov -= scale * 0.005;
        if (scrollYOffsetStore > 0 && fov - 0.005 * scale > fovStore - scale) fov -= scale * 0.005;
        if (fov < 1.0f) fov = 1.0f;
        if (fov > 45.0f) fov = 45.0f;
    }
}
