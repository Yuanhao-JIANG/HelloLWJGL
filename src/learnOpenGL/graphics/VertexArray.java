package learnOpenGL.graphics;

import learnOpenGL.utils.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexArray {
    private int vao, vbo, ebo;
    private final float[] vertices;
    private final int[] indices;

    public VertexArray(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public void create() {
        vbo = GL15.glGenBuffers();
        ebo = GL15.glGenBuffers();

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        // actually just vertices for the second would be accepted
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL15.GL_STATIC_DRAW);

        // The last element buffer object that gets bound while a VAO is bound, is stored as the VAO's element buffer
        // object. Binding to a VAO then also automatically binds that EBO.
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
        // actually just indices for the second would be accepted
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 0);
        GL30.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES,
                3 * Float.BYTES);
        GL30.glEnableVertexAttribArray(1);

        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 8 * Float.BYTES,
                6 * Float.BYTES);
        GL30.glEnableVertexAttribArray(2);
        /*Each vertex attribute takes its data from memory managed by a VBO and which VBO it takes its data from (you
        can have multiple VBOs) is determined by the VBO currently bound to GL_ARRAY_BUFFER when calling
        glVertexAttribPointer. Since the previously defined VBO is still bound before calling glVertexAttribPointer
        vertex attribute 0 is now associated with its vertex data.*/

        //now the vertex attribute 0 of vao1 is pointed to vbo1, we can unbind the buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        // remember: do NOT unbind the EBO while a VAO is active as the bound element buffer object is stored in the
        // VAO; keep the EBO bound.
        GL30.glBindVertexArray(0);
        //but you can unbind the ebo after unbind the vao
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        /* For GL_ARRAY_BUFFER the VAO will save the binding when you call glVertexAttribPointer. It is basically
         because the GL_ARRAY_BUFFER binding is not part of the VAO's state. So calling
         glBindBuffer(GL_ARRAY_BUFFER, vertexBufferHandle) won't do anything with the VAO's state.

         For GL_ELEMENT_ARRAY_BUFFER it's not the case: the VAO (if bound) will save your index buffer binding to it's
         state when you call glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferHandle). */
    }

    public static void draw(int count) {
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL); // default is GL11.GL_Fill, to just draw use ..._LINE
        GL30.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
    }

    public int getVao() {
        return vao;
    }
}
