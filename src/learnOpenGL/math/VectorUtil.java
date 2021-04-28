package learnOpenGL.math;

import org.joml.Vector3f;

public class VectorUtil {
    public static Vector3f addTwo(Vector3f v1, Vector3f v2) {
        Vector3f dest = new Vector3f();
        v1.add(v2, dest);
        return dest;
    }

    public static Vector3f scalar(Vector3f v, float scalar) {
        Vector3f dest = new Vector3f();
        v.mul(scalar, dest);
        return dest;
    }

    public static Vector3f crossTwo(Vector3f v1, Vector3f v2) {
        Vector3f dest = new Vector3f();
        v1.cross(v2, dest);
        return dest;
    }
}
