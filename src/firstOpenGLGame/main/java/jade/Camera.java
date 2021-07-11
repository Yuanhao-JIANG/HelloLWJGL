package firstOpenGLGame.main.java.jade;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    public Vector3f camPos = new Vector3f();
    public Vector3f camUp = new Vector3f();
    public Vector3f camFront = new Vector3f();
    public Vector3f camTarget = new Vector3f();
    private final Matrix4f orthoProjectionMatrix = new Matrix4f().setOrtho(
            0f, 960f, 0f, 540f, 0.1f, 100f);
    private final Matrix4f viewMatrix = new Matrix4f();
    private final Matrix4f inverseOrthoProjectionMatrix = new Matrix4f();
    private final Matrix4f inverseViewMatrix = new Matrix4f();
    public Camera() {
        camPos.set(0.0f, 0.0f, 20.0f);
        camUp.set(0.0f, 1.0f, 0.0f);
        camFront.set(0.0f, 0.0f, -1.0f);
        camTarget.set(0.0f, 0.0f, 0.0f);
        orthoProjectionMatrix.invert(inverseOrthoProjectionMatrix);
    }

    private void updateViewMatrix() {
        viewMatrix.setLookAt(camPos, camTarget, camUp);
        viewMatrix.invert(inverseViewMatrix);
    }
    public Matrix4f getViewMatrix() {
        updateViewMatrix();
        return viewMatrix;
    }

    public Matrix4f getOrthoProjectionMatrix() {
        return orthoProjectionMatrix;
    }

    public Matrix4f getInverseViewMatrix() {
        return inverseViewMatrix;
    }

    public Matrix4f getInverseOrthoProjectionMatrix() {
        return inverseOrthoProjectionMatrix;
    }
}
