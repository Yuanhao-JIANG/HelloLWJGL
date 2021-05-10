package firstOpenGLGame.main.java.components;

import firstOpenGLGame.main.java.jade.Component;
import org.joml.Vector4f;

public class Sprite extends Component {
    private final Vector4f color;

    public Sprite(Vector4f color) {
        this.color = color;
    }

    @Override
    public void start() {}

    @Override
    public void update(float dt) {}

    public Vector4f getColor() {
        return color;
    }
}