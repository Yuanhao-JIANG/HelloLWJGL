package learnOpenGL.math;

public class VelocityUtil {

    public static float getVelocity(float v0, float acceleration, float timePassed) {
        return v0 + acceleration * timePassed;
    }
}
