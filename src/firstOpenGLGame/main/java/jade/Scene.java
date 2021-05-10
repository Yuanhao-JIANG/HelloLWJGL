package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private boolean isRunning = false;
    protected final List<GameObject> gameObjects = new ArrayList<>();
    protected Camera camera;
    protected Renderer renderer;
    public Scene() {

    }

    public void init() {}

    public void start() {
        for (GameObject gameObject : gameObjects) {
            gameObject.start();
            renderer.addGameObject(gameObject);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        gameObjects.add(gameObject);
        if (isRunning) {
            gameObject.start();
            renderer.addGameObject(gameObject);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public abstract void update(float dt);
}
