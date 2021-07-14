package firstOpenGLGame.main.java.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import firstOpenGLGame.main.java.components.Component;
import firstOpenGLGame.main.java.components.ComponentDeserializer;
import firstOpenGLGame.main.java.components.ComponentSerializer;
import firstOpenGLGame.main.java.jade.Camera;
import firstOpenGLGame.main.java.jade.GameObject;
import firstOpenGLGame.main.java.jade.GameObjectDeserializer;
import firstOpenGLGame.main.java.renderer.Renderer;
import imgui.ImGui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private boolean isRunning = false;
    protected final List<GameObject> gameObjects = new ArrayList<>();
    protected Camera camera;
    protected Renderer renderer;
    protected GameObject activeGameObject = null;
    protected Boolean levelLoaded = false;

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

    public void sceneImGUI() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imGui();
            ImGui.end();
        }

        imGui();
    }

    public void imGui() {}

    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentSerializer())
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        try {
            FileWriter fileWriter = new FileWriter("level.txt", false);
            fileWriter.write(gson.toJson(gameObjects));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File file = new File("level.txt");
        if (!file.exists()) return;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentSerializer())
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        String inputFile = "";
        try {
            inputFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.equals("")) {
            GameObject[] loadedGameObjects = gson.fromJson(inputFile, GameObject[].class);
            for (GameObject loadedGameObject : loadedGameObjects) {
                addGameObjectToScene(loadedGameObject);
            }

            levelLoaded = true;
        }
    }
}
