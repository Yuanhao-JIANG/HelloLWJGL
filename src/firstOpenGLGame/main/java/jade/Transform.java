package firstOpenGLGame.main.java.jade;

import org.joml.Vector2f;

public class Transform {
    public Vector2f position;
    public Vector2f scale;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    private void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copyTo(Transform dest) {
        dest.position.set(this.position);
        dest.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Transform)) return false;
        Transform that = (Transform) o;
        return this.position.equals(that.position) && this.scale.equals(that.scale);
    }
}
