package learnOpenGL.window;

import org.joml.Vector3f;

public class Camera {
    float myHeight = 0.2f;
    Vector3f cameraPos = new Vector3f(0.0f, -2.2f + myHeight, 3.0f);
    Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    Vector3f cameraTarget = new Vector3f();

    public Camera() {

    }
}
