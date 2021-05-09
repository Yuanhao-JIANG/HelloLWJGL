package learnOpenGL.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private final IntBuffer width = BufferUtils.createIntBuffer(1);
    private final IntBuffer height = BufferUtils.createIntBuffer(1);
    private final IntBuffer nrChannels = BufferUtils.createIntBuffer(1);
    private final int textureID;

    public Texture(String path) {
        textureID = load(path);
    }

    private int load(String path) {
        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        stbi_set_flip_vertically_on_load(true);
        ByteBuffer data = stbi_load(path, width, height, nrChannels, 0);
        assert data != null;

        if (nrChannels.get(0) == 3) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, data);
        } else if (nrChannels.get(0) == 4) {
            //to enable alpha value in images (png)
            glEnable(GL_BLEND);// you enable blending function
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        }
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);

        stbi_image_free(data);

        return result;
    }

    public static void upload(Shader shader, String uniformName, int uniformIndex) {
        glUniform1i(glGetUniformLocation(shader.getShaderProgram(), uniformName), uniformIndex);
    }

    public static void bind(int textureIndicator, Texture texture) {
        glActiveTexture(textureIndicator);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }

    public int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width.get(0);
    }

    public int getHeight() {
        return height.get(0);
    }
}
