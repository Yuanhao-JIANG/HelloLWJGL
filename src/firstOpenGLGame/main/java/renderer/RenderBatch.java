package firstOpenGLGame.main.java.renderer;

import firstOpenGLGame.main.java.components.Sprite;
import firstOpenGLGame.main.java.jade.Window;
import firstOpenGLGame.main.java.util.ShaderUtils;

import static firstOpenGLGame.main.java.util.ShaderUtils.uploadMatrix4f;
import static firstOpenGLGame.main.java.util.VAOUtil.createVAO2;
import static firstOpenGLGame.main.java.util.VAOUtil.draw;
import static firstOpenGLGame.main.java.util.VerticesUtil.generateIndices;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class RenderBatch {
    private final Sprite[] sprites;
    private final int maxSpritesNum;
    private int spriteNum;
    private final float[] vertices;
    private int shaderID, vao, vbo;

    public RenderBatch(int maxSpritesNum, String vertShaderPath, String fragShaderPath) {
        this.maxSpritesNum = maxSpritesNum;
        sprites = new Sprite[maxSpritesNum];
        spriteNum = 0;
        vertices = new float[maxSpritesNum * 4 * 6];
        shaderID = ShaderUtils.load(vertShaderPath, fragShaderPath);
    }

    public void start() {
        int[] indices = generateIndices(maxSpritesNum);
        int[] objects = createVAO2(vertices, indices, GL_DYNAMIC_DRAW, GL_STATIC_DRAW);
        vao = objects[0];
        vbo = objects[1];
    }

    public void addSprite(Sprite sprite) {
        sprites[spriteNum] = sprite;

        //update vertices array
        int yScaleIndicator;
        int xScaleIndicator;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                xScaleIndicator = 0;
                yScaleIndicator = 0;
            } else if (i == 1) {
                xScaleIndicator = 1;
                yScaleIndicator = 0;
            } else if (i == 2) {
                xScaleIndicator = 1;
                yScaleIndicator = 1;
            } else {
                xScaleIndicator = 0;
                yScaleIndicator = 1;
            }

            int offset = spriteNum * 4 * 6 + 6 * i;
            vertices[offset]     = sprite.gameObject.transform.position.x + xScaleIndicator *
                    sprite.gameObject.transform.scale.x;
            vertices[offset + 1] = sprite.gameObject.transform.position.y + yScaleIndicator *
                    sprite.gameObject.transform.scale.y;
            vertices[offset + 2] = sprite.getColor().x;
            vertices[offset + 3] = sprite.getColor().y;
            vertices[offset + 4] = sprite.getColor().z;
            vertices[offset + 5] = sprite.getColor().w;
        }

        spriteNum++;
    }

    public void render() {
        //update vertices
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        glUseProgram(shaderID);
        uploadMatrix4f(shaderID, "uProjection",
                Window.getCurrentScene().getCamera().getOrthoProjectionMatrix());
        uploadMatrix4f(shaderID, "uView",
                Window.getCurrentScene().getCamera().getViewMatrix());
        glBindVertexArray(vao);
        draw(spriteNum * 6);

        glUseProgram(0);
        glBindVertexArray(0);
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public boolean hasRoom() {
        return spriteNum < maxSpritesNum;
    }
}
