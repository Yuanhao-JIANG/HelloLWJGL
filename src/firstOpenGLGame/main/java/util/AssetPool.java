package firstOpenGLGame.main.java.util;

import java.util.HashMap;

public class AssetPool {
    private static final HashMap<String, Integer> shaderIDs = new HashMap<>();
    private static final HashMap<String, Integer> textureIDs = new HashMap<>();

    public static Integer getShaderID(String vertPath, String fragPath) {
        String key = vertPath + fragPath;
        if (!shaderIDs.containsKey(key)) shaderIDs.put(key, ShaderUtils.load(vertPath, fragPath));
        return shaderIDs.get(key);
    }

    public static Integer getTextureID(String imagePath) {
        if (!textureIDs.containsKey(imagePath)) textureIDs.put(imagePath, TextureUtil.genTextureID(imagePath));
        return textureIDs.get(imagePath);
    }
}
