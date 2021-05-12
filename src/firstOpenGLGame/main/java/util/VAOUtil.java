package firstOpenGLGame.main.java.util;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VAOUtil {
    public static int[] createVAO(float[] vertexArray, int[] indices, int arrayBufferUsage,
                                 int elementArrayBufferUsage) {
        int vao = glGenVertexArrays();
        int vbo = glGenBuffers(), ebo = glGenBuffers();
        int[] objects = new int[2];

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexArray, arrayBufferUsage);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, elementArrayBufferUsage);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 9 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 9 * Float.BYTES,
                2 * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 9 * Float.BYTES,
                6 * Float.BYTES);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(3, 1, GL_FLOAT, false, 9 * Float.BYTES,
                8 * Float.BYTES);
        glEnableVertexAttribArray(3);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);

        objects[0] = vao;
        objects[1] = vbo;

        return objects;
    }

    public static int[] createVAO2(float[] vertexArray, int[] indices, int arrayBufferUsage,
                                 int elementArrayBufferUsage) {
        int[] objects = new int[2];
        int vao = glGenVertexArrays();
        int vbo = glGenBuffers(), ebo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexArray, arrayBufferUsage);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, elementArrayBufferUsage);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 6 * Float.BYTES,
                2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        objects[0] = vao;
        objects[1] = vbo;
        return objects;
    }

    public static void draw(int count) {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
    }
}
