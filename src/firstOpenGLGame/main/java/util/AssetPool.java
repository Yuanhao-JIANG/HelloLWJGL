package firstOpenGLGame.main.java.util;

import firstOpenGLGame.main.java.components.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static final Map<String, Integer> shaderIDs = new HashMap<>();
    private static final Map<String, Integer[]> textureInfos = new HashMap<>();
    private static final Map<String, SpriteSheet> spriteSheets = new HashMap<>();

    public static Integer getShaderID(String vertPath, String fragPath) {
        String key = vertPath + fragPath;
        if (!shaderIDs.containsKey(key)) shaderIDs.put(key, ShaderUtils.load(vertPath, fragPath));
        return shaderIDs.get(key);
    }

    public static Integer[] getTextureInfo(String imagePath) {
        if (!textureInfos.containsKey(imagePath)) textureInfos.put(imagePath, TextureUtil.genTextureID(imagePath));
        return textureInfos.get(imagePath);
    }

    public static void addSpriteSheet(String spriteSheetPath, int spriteWidth,
                                      int spriteHeight, int spriteNum, int spacingX, int spacingY) {
        if (!spriteSheets.containsKey(spriteSheetPath)) {
            SpriteSheet spriteSheetToBeAdd = new SpriteSheet();
            spriteSheetToBeAdd.init(getTextureInfo(spriteSheetPath), spriteWidth, spriteHeight, spriteNum, spacingX, spacingY);
            spriteSheets.put(spriteSheetPath, spriteSheetToBeAdd);
        }
    }

    public static SpriteSheet getSpriteSheet(String spriteSheetPath) {
        assert spriteSheets.containsKey(spriteSheetPath) : "sprite sheet not added";
        return spriteSheets.get(spriteSheetPath);
    }
}
