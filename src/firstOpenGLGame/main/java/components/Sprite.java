package firstOpenGLGame.main.java.components;

import firstOpenGLGame.main.java.jade.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Sprite extends Component {
    private Vector4f color;
    private int textureID;
    private Vector2f[] texCoords;

    public Sprite(Vector4f color) {
        this.color = color;
    }

    public Sprite(int textureID) {
        this.textureID = textureID;
        this.texCoords = new Vector2f[] {
                new Vector2f(0.0f, 1.0f),
                new Vector2f(1.0f, 1.0f),
                new Vector2f(1.0f, 0.0f),
                new Vector2f(0.0f, 0.0f)
        };
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Sprite(int textureID, Vector2f[] texCoords) {
        this.textureID = textureID;
        this.texCoords = texCoords;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float dt) {
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public Vector4f getColor() {
        return color;
    }

    public int getTextureID() {
        return textureID;
    }

    public Vector2f[] getTexCoords() {
        return texCoords;
    }
}
