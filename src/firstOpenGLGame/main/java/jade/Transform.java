package firstOpenGLGame.main.java.jade;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform {
    public Vector3f position;
    public Vector2f scale;

    public Transform() {
        init(new Vector3f(), new Vector2f());
    }

    public Transform(Vector3f position, Vector2f scale) {
        init(position, scale);
    }

    private void init(Vector3f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector3f(this.position), new Vector2f(this.scale));
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
