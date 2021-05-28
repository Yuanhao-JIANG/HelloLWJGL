package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.components.Sprite;
import firstOpenGLGame.main.java.components.SpriteSheet;
import firstOpenGLGame.main.java.renderer.Renderer;
import firstOpenGLGame.main.java.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class LevelEditorScene extends Scene{
    private GameObject obj1;
    private GameObject obj2;
    private GameObject obj3;
    private GameObject obj4;
    private SpriteSheet spriteSheet;
    public LevelEditorScene() {}

    @Override
    public void init() {
        loadResources();
        spriteSheet = AssetPool.getSpriteSheet("src/firstOpenGLGame/assets/images/spriteSheet.png");

        camera = new Camera();
        renderer = new Renderer();

        obj1 = new GameObject("mario",
                new Transform(new Vector3f(100, 400, 0), new Vector2f(256, 256)));
        obj1.addComponent(new Sprite(spriteSheet.getSprite(1)));
        addGameObjectToScene(obj1);

        obj2 = new GameObject("goomba",
                new Transform(new Vector3f(400, 400, -1), new Vector2f(256, 256)));
        obj2.addComponent(new Sprite(spriteSheet.getSprite(14)));
        addGameObjectToScene(obj2);

        obj3 = new GameObject("greenSquare",
                new Transform(new Vector3f(300, 520, 1), new Vector2f(100, 100)));
        obj3.addComponent(new Sprite(AssetPool.getTextureInfo("src/firstOpenGLGame/assets/images/greenSquare.png")[0]));
        addGameObjectToScene(obj3);

        obj4 = new GameObject("redSquare",
                new Transform(new Vector3f(350, 520, 0), new Vector2f(100, 100)));
        obj4.addComponent(new Sprite(AssetPool.getTextureInfo("src/firstOpenGLGame/assets/images/redSquare.png")[0]));
        addGameObjectToScene(obj4);
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
            obj1.getComponent(Sprite.class).reset(spriteSheet.getSprite(spriteIndex1));
            obj2.getComponent(Sprite.class).reset(spriteSheet.getSprite(spriteIndex2));
        }
        //obj1.transform.position.x += 150 * dt;
        //obj2.transform.position.x += 150 * dt;
        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }

        renderer.render();
    }
}
