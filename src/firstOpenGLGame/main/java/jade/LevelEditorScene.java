package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.components.SpriteSheet;
import firstOpenGLGame.main.java.renderer.Renderer;
import firstOpenGLGame.main.java.util.AssetPool;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene{
    public LevelEditorScene() {}

    @Override
    public void init() {
        loadResources();
        SpriteSheet spriteSheet = AssetPool.getSpriteSheet("src/firstOpenGLGame/assets/images/spriteSheet.png");

        camera = new Camera();
        renderer = new Renderer();

        GameObject obj1 = new GameObject("mario",
                new Transform(new Vector2f(100, 400), new Vector2f(256, 256)));
        obj1.addComponent(spriteSheet.getSprite(0));
        addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("goomba",
                new Transform(new Vector2f(400, 400), new Vector2f(256, 256)));
        obj2.addComponent(spriteSheet.getSprite(1));
        addGameObjectToScene(obj2);
    }

    public void loadResources() {
        AssetPool.getShaderID("src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");

        AssetPool.addSpriteSheet("src/firstOpenGLGame/assets/images/spriteSheet.png", 16,
                16, 26, 0, 0);
    }

    @Override
    public void update(float dt) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }

        renderer.render();
    }
}
