package firstOpenGLGame.main.java.renderer;

import firstOpenGLGame.main.java.components.Sprite;
import firstOpenGLGame.main.java.jade.Window;
import firstOpenGLGame.main.java.util.AssetPool;

import java.util.ArrayList;
import java.util.List;

import static firstOpenGLGame.main.java.util.ShaderUtils.*;
import static firstOpenGLGame.main.java.util.VAOUtil.createVAO;
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
    private List<Integer> textureIDs;
    private final int[] textUnitIndexArray = {0, 1, 2, 3, 4, 5, 6, 7};

    public RenderBatch(int maxSpritesNum, String vertPath, String fragPath) {
        this.maxSpritesNum = maxSpritesNum;
        sprites = new Sprite[maxSpritesNum];
        spriteNum = 0;
        vertices = new float[maxSpritesNum * 4 * 9];
        shaderID = AssetPool.getShaderID(vertPath, fragPath);
        textureIDs = new ArrayList<>();
    }

    public void start() {
        int[] indices = generateIndices(maxSpritesNum);
        int[] objects = createVAO(vertices, indices, GL_DYNAMIC_DRAW, GL_STATIC_DRAW);
        vao = objects[0];
        vbo = objects[1];
    }

    public void addSprite(Sprite sprite) {
        sprites[spriteNum] = sprite;

        int textureID = sprite.getTextureID();
        if (textureID != 0) {
            if (!textureIDs.contains(textureID)) textureIDs.add(textureID);
        }

        loadSpriteToVertex(spriteNum);

        spriteNum++;
    }

    public void loadSpriteToVertex(int index) {
        Sprite sprite = sprites[index];

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
                yScaleIndicator = -1;
            } else {
                xScaleIndicator = 0;
                yScaleIndicator = -1;
            }

            int offset = index * 4 * 9 + 9 * i;
            //position
            vertices[offset]     = sprite.gameObject.transform.position.x + xScaleIndicator *
                    sprite.gameObject.transform.scale.x;
            vertices[offset + 1] = sprite.gameObject.transform.position.y + yScaleIndicator *
                    sprite.gameObject.transform.scale.y;
            //color
            vertices[offset + 2] = sprite.getColor().x;
            vertices[offset + 3] = sprite.getColor().y;
            vertices[offset + 4] = sprite.getColor().z;
            vertices[offset + 5] = sprite.getColor().w;
            //texture coordinates
            vertices[offset + 6] = sprite.getTexCoords()[i].x;
            vertices[offset + 7] = sprite.getTexCoords()[i].y;
            //texture id
            vertices[offset + 8] = (float) sprite.getTextureID();
        }
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
        for (int i = 0; i < textureIDs.size(); i++) {
            bindTexture(GL_TEXTURE0 + i, textureIDs.get(i));
        }
        setTextUnitArray(shaderID, "uTextures", textUnitIndexArray);
        glBindVertexArray(vao);
        draw(spriteNum * 6);

        glUseProgram(0);
        glBindVertexArray(0);
        unbindTexture();
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public boolean hasRoom() {
        return spriteNum < maxSpritesNum;
    }

    public boolean hasTextureRoom() {
        return textureIDs.size() < 8;
    }

    public boolean hasTexture(int textureID) {
        return textureIDs.contains(textureID);
    }
}
