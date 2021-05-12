package firstOpenGLGame.main.java.components;

import firstOpenGLGame.main.java.jade.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Sprite extends Component {
    private final Vector4f color;
    private int textureID;
    private final Vector2f[] texCoords = {
            new Vector2f(0.0f, 1.0f),
            new Vector2f(1.0f, 1.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(0.0f, 0.0f),
    };

    public Sprite(Vector4f color) {
        this.color = color;
    }

    public Sprite(int textureID) {
        this.textureID = textureID;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float dt) {
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
