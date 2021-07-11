package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.components.Rigidbody;
import firstOpenGLGame.main.java.components.Sprite;
import firstOpenGLGame.main.java.components.SpriteSheet;
import firstOpenGLGame.main.java.renderer.Renderer;
import firstOpenGLGame.main.java.util.AssetPool;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static firstOpenGLGame.main.java.jade.Input.*;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class LevelEditorScene extends Scene{
    private SpriteSheet spriteSheet;
    private SpriteSheet blocks;
    boolean needReload = true;
    public LevelEditorScene() {}

    @Override
    public void init() {
        loadResources();
        spriteSheet = AssetPool.getSpriteSheet("src/firstOpenGLGame/assets/images/spriteSheet.png");
        blocks = AssetPool.getSpriteSheet("src/firstOpenGLGame/assets/images/decorationsAndBlocks.png");

        camera = new Camera();
        renderer = new Renderer();

        if (!levelLoaded || needReload) {
            gameObjects.clear();
            System.out.println("loading...");

            //initiate, create or active game object
            GameObject obj0 = new GameObject("mario",
                    new Transform(new Vector3f(100, 400, 0), new Vector2f(256, 256)));
            Sprite obj0Sprite = new Sprite();
            obj0Sprite.setSprite(spriteSheet.getSprite(1));
            obj0.addComponent(obj0Sprite);
            addGameObjectToScene(obj0);

            GameObject obj1 = new GameObject("goomba",
                    new Transform(new Vector3f(400, 400, -1), new Vector2f(256, 256)));
            Sprite obj1Sprite = new Sprite();
            obj1Sprite.setSprite(spriteSheet.getSprite(14));
            obj1.addComponent(obj1Sprite);
            addGameObjectToScene(obj1);

            GameObject obj2 = new GameObject("greenSquare",
                    new Transform(new Vector3f(300, 520, 0), new Vector2f(100, 100)));
            Sprite obj2Sprite = new Sprite();
            obj2Sprite.setTextureID(AssetPool
                    .getTextureInfo("src/firstOpenGLGame/assets/images/greenSquare.png")[0]);
            obj2.addComponent(obj2Sprite);
            obj2.addComponent(new Rigidbody());
            addGameObjectToScene(obj2);

            GameObject obj3 = new GameObject("redSquare",
                    new Transform(new Vector3f(350, 520, 1), new Vector2f(100, 100)));
            Sprite obj3Sprite = new Sprite();
            obj3Sprite.setTextureID(AssetPool
                    .getTextureInfo("src/firstOpenGLGame/assets/images/redSquare.png")[0]);
            obj3.addComponent(obj3Sprite);
            addGameObjectToScene(obj3);
        }

        activeGameObject = gameObjects.get(2);
    }

    public void loadResources() {
        AssetPool.addShaderID("src/firstOpenGLGame/assets/shaders/vertex.glsl",
                "src/firstOpenGLGame/assets/shaders/fragment.glsl");

        AssetPool.addSpriteSheet("src/firstOpenGLGame/assets/images/spriteSheet.png",
                16, 16, 26, 0, 0);

        AssetPool.addSpriteSheet("src/firstOpenGLGame/assets/images/decorationsAndBlocks.png",
                16, 16, 81, 0, 0);

        AssetPool.addTextureInfo("src/firstOpenGLGame/assets/images/greenSquare.png");
        AssetPool.addTextureInfo("src/firstOpenGLGame/assets/images/redSquare.png");
    }

    private int spriteIndex1 = 1;
    private int spriteIndex2 = 14;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {
        if (isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) System.out.println(getWorldMouseX() + ", " + getWorldMouseY());
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
            gameObjects.get(0).getComponent(Sprite.class).reset(spriteSheet.getSprite(spriteIndex1));
            gameObjects.get(1).getComponent(Sprite.class).reset(spriteSheet.getSprite(spriteIndex2));
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

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowRight = windowPos.x + windowSize.x;
        for (int i = 0; i < blocks.getSize(); i++) {
            Sprite sprite = blocks.getSprite(i);
            float spriteWidth = sprite.getSize().x;
            float spriteHeight = sprite.getSize().y;
            int id = sprite.getTextureID();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth * 2, spriteHeight * 2,
                    texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                System.out.println("Button " + i + " clicked.");
            }
            ImGui.popID();

            ImVec2 buttonPos = new ImVec2();
            ImGui.getItemRectMax(buttonPos);
            if (i + 1 < blocks.getSize() && buttonPos.x + itemSpacing.x +spriteWidth < windowRight) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
