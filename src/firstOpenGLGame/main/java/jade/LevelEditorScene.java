package firstOpenGLGame.main.java.jade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import firstOpenGLGame.main.java.components.Sprite;
import firstOpenGLGame.main.java.components.SpriteSheet;
import firstOpenGLGame.main.java.renderer.Renderer;
import firstOpenGLGame.main.java.util.AssetPool;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class LevelEditorScene extends Scene{
    private GameObject obj0;
    private GameObject obj1;
    private GameObject obj2;
    private GameObject obj3;
    private SpriteSheet spriteSheet;
    public LevelEditorScene() {}

    @Override
    public void init() {
        loadResources();
        spriteSheet = AssetPool.getSpriteSheet("src/firstOpenGLGame/assets/images/spriteSheet.png");

        camera = new Camera();
        renderer = new Renderer();

        obj0 = new GameObject("mario",
                new Transform(new Vector3f(100, 400, 0), new Vector2f(256, 256)));
        Sprite obj0Sprite = new Sprite();
        obj0Sprite.setSprite(spriteSheet.getSprite(1));
        obj0.addComponent(obj0Sprite);
        addGameObjectToScene(obj0);

        obj1 = new GameObject("goomba",
                new Transform(new Vector3f(400, 400, -1), new Vector2f(256, 256)));
        Sprite obj1Sprite = new Sprite();
        obj1Sprite.setSprite(spriteSheet.getSprite(14));
        obj1.addComponent(obj1Sprite);
        addGameObjectToScene(obj1);

        obj2 = new GameObject("greenSquare",
                new Transform(new Vector3f(300, 520, 0), new Vector2f(100, 100)));
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTextureID(AssetPool.getTextureInfo("src/firstOpenGLGame/assets/images/greenSquare.png")[0]);
        obj2.addComponent(obj2Sprite);
        addGameObjectToScene(obj2);

        obj3 = new GameObject("redSquare",
                new Transform(new Vector3f(350, 520, 1), new Vector2f(100, 100)));
        Sprite obj3Sprite = new Sprite();
        obj3Sprite.setTextureID(AssetPool.getTextureInfo("src/firstOpenGLGame/assets/images/redSquare.png")[0]);
        obj3.addComponent(obj3Sprite);
        addGameObjectToScene(obj3);

        activeGameObject = obj2;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentSerializer())
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        String serialized = gson.toJson(obj0);
        System.out.println(serialized);
        GameObject deserialized = gson.fromJson(serialized, GameObject.class);
        System.out.println(deserialized);
    }

    public void loadResources() {
        AssetPool.getShaderID("src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");

        AssetPool.addSpriteSheet("src/firstOpenGLGame/assets/images/spriteSheet.png", 16,
                16, 26, 0, 0);
    }

    private int spriteIndex1 = 1;
    private int spriteIndex2 = 14;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft < 0) {
            spriteFlipTimeLeft = 0.2f;
            spriteIndex1 ++;
            spriteIndex2 ++;
            if (spriteIndex1 > 3) {
                spriteIndex1 = 1;
            }
            if (spriteIndex2 > 15) {
                spriteIndex2 = 14;
            }
            obj0.getComponent(Sprite.class).reset(spriteSheet.getSprite(spriteIndex1));
            obj1.getComponent(Sprite.class).reset(spriteSheet.getSprite(spriteIndex2));
        }
        //obj0.transform.position.x += 150 * dt;
        //obj1.transform.position.x += 150 * dt;
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }

        renderer.render();
    }

    @Override
    public void imGui() {
        ImGui.begin("Test window");
        ImGui.text("Testing, testing.");
        ImGui.end();
    }
}
