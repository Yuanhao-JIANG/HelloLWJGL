package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.components.Sprite;
import firstOpenGLGame.main.java.renderer.Renderer;
import firstOpenGLGame.main.java.util.AssetPool;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene{
    public LevelEditorScene() {}

    @Override
    public void init() {
        camera = new Camera();
        renderer = new Renderer();

        AssetPool.getShaderID("src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");

        GameObject obj1 = new GameObject("mario",
                new Transform(new Vector2f(100, 400), new Vector2f(256, 256)));
        obj1.addComponent(new Sprite(AssetPool.getTextureID("src/firstOpenGLGame/assets/images/mario.png")));
        addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("goomba",
                new Transform(new Vector2f(400, 400), new Vector2f(256, 256)));
        obj2.addComponent(new Sprite(AssetPool.getTextureID("src/firstOpenGLGame/assets/images/goomba.png")));
        addGameObjectToScene(obj2);
    }

    @Override
    public void update(float dt) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }

        renderer.render();
    }
}
