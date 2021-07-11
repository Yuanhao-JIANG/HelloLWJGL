package firstOpenGLGame.main.java.components;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {
    private Integer[] textureInfo = null;
    private List<Sprite> sprites = null;

    public SpriteSheet() {}
    public void init(Integer[] textureIDInfo, int spriteWidth, int spriteHeight, int spriteNum, int spacingX,
                     int spacingY) {
        sprites = new ArrayList<>();
        this.textureInfo = textureIDInfo;
        int currentX = 0;
        int currentY = textureIDInfo[2] - spriteHeight;
        for (int i = 0; i < spriteNum; i++) {
            float leftX = currentX / (float) textureIDInfo[1];
            float rightX = (currentX + spriteWidth) /  (float) textureIDInfo[1];
            float bottomY = currentY / (float) textureIDInfo[2];
            float topY = (currentY + spriteHeight) / (float) textureIDInfo[2];

            Vector2f[] texCoords = {
                    new Vector2f(leftX, topY),
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY)
            };
            Sprite sprite = new Sprite();
            sprite.setTextureID(textureIDInfo[0]);
            sprite.setTexCoords(texCoords);
            sprite.setSize(spriteWidth, spriteHeight);
            sprites.add(sprite);

            currentX += spriteWidth + spacingX;
            if (currentX >= textureIDInfo[1]) {
                currentX = 0;
                currentY -= spriteHeight + spacingY;
            }
        }
    }

    public Sprite getSprite(int index) {
        return sprites.get(index);
    }

    public Integer[] getTextureInfo() {
        return textureInfo;
    }

    public int getSize() {
        return sprites.size();
    }
}
