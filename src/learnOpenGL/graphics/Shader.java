package learnOpenGL.graphics;

import learnOpenGL.utils.ShaderUtils;
import org.lwjgl.opengl.GL20;

public class Shader {
    private final int shaderProgram;

    public Shader(String vertex, String fragment) {
        shaderProgram = ShaderUtils.load(vertex, fragment);
    }

    public void loadProgram() {
        GL20.glUseProgram(shaderProgram);
    }

    public int getShaderProgram() {
        return shaderProgram;
    }
}
