package firstOpenGLGame.main.java.util;

import org.joml.Matrix4f;

import static firstOpenGLGame.main.java.util.FileUtils.loadAsString;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
    private ShaderUtils() {}

    public static int load(String vertPath, String fragPath) {
        String vert = loadAsString(vertPath);
        String frag = loadAsString(fragPath);
        return create(vert, frag);
    }

    private static int create(String vert, String frag) {
        int programID = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, vert);
        glShaderSource(fragID, frag);

        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println("Failed to compile vertex shader!");
            System.out.println(glGetShaderInfoLog(vertID));
            return -1;
        }
        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println("Failed to compile fragment shader!");
            System.out.println(glGetShaderInfoLog(fragID));
            return -1;
        }

        glAttachShader(programID, vertID);
        glAttachShader(programID, fragID);
        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            System.out.println("Failed to link shader program!");
            System.out.println(glGetProgramInfoLog(programID));
            return -1;
        }

        glDeleteShader(vertID);
        glDeleteShader(fragID);

        return programID;
    }

    public static void uploadMatrix4f(int shaderProgram, String matName,
                               Matrix4f matrix4f) {
        float[] matArray = new float[16];
        matrix4f.get(matArray);
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, matName),
                false, matArray);
    }
}
