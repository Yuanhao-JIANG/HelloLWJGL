package firstOpenGLGame.main.java.util;

public class VerticesUtil {
    private VerticesUtil() {}
    public static int[] generateIndices(int maxSpritesNum) {
        int[] indices = new int[maxSpritesNum * 6];
        for (int i = 0; i < maxSpritesNum; i++) {
            //top left triangle
            indices[6 * i]     = 4 * i;
            indices[6 * i + 1] = 4 * i + 1;
            indices[6 * i + 2] = 4 * i + 3;
            //bottom right triangle
            indices[6 * i + 3] = 4 * i + 1;
            indices[6 * i + 4] = 4 * i + 2;
            indices[6 * i + 5] = 4 * i + 3;
        }
        return indices;
    }
}
