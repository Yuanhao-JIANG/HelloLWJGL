package firstOpenGLGame.main.java.renderer;

import firstOpenGLGame.main.java.components.Sprite;
import firstOpenGLGame.main.java.jade.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private final List<RenderBatch> batches;

    public Renderer() {
        batches =  new ArrayList<>();
    }

    public void addGameObject(GameObject gameObject) {
        Sprite sprite = gameObject.getComponent(Sprite.class);
        if (sprite != null) {
            addSprite(sprite);
        }
    }

    private void addSprite(Sprite sprite) {
        boolean isAdded = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom()) {
                batch.addSprite(sprite);
                isAdded = true;
                break;
            }
        }

        if (!isAdded) {
            RenderBatch newbatch = new RenderBatch(MAX_BATCH_SIZE,
                    "src/firstOpenGLGame/assets/shaders/vertex.glsl",
                    "src/firstOpenGLGame/assets/shaders/fragment.glsl");
            newbatch.start();
            batches.add(newbatch);
            newbatch.addSprite(sprite);
        }
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }
}
