package learnOpenGL.graphics;

import org.joml.Vector3f;

public class Data {
    //cubes
    private static final float[] cube = {
            // positions          // colors           // texture coords
            //front
             0.5f,  0.5f, 0.5f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
             0.5f, -0.5f, 0.5f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
            -0.5f, -0.5f, 0.5f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
            -0.5f,  0.5f, 0.5f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f,   // top left

            //back
             0.5f,  0.5f, -0.5f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
             0.5f, -0.5f, -0.5f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
            -0.5f, -0.5f, -0.5f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
            -0.5f,  0.5f, -0.5f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f,  // top left

            //top
             0.5f, 0.5f, -0.5f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
             0.5f, 0.5f,  0.5f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
            -0.5f, 0.5f,  0.5f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
            -0.5f, 0.5f, -0.5f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f,   // top left

            //bottom
             0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
             0.5f, -0.5f,  0.5f,  0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
            -0.5f, -0.5f, -0.5f,  1.0f, 1.0f, 0.0f,   0.0f, 1.0f,   // top left

            //right
            0.5f,  0.5f, -0.5f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
            0.5f, -0.5f, -0.5f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
            0.5f, -0.5f,  0.5f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
            0.5f,  0.5f,  0.5f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f,   // top left

            //left
            -0.5f,  0.5f, -0.5f,  1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
            -0.5f,  0.5f,  0.5f,  1.0f, 1.0f, 0.0f,   0.0f, 1.0f    // top left
    };

    private static final int[] cubeIndices = {
            0,1,3,
            1,2,3,

            4,5,7,
            5,6,7,

            8,9,11,
            9,10,11,

            12,13,15,
            13,14,15,

            16,17,19,
            17,18,19,

            20,21,23,
            21,22,23
    };

    private static final Vector3f[] cubePositions = {
            new Vector3f( 0.0f,  0.0f,  0.0f),
            new Vector3f( 2.0f,  5.0f, -15.0f),
            new Vector3f(-1.5f, -2.2f, -2.5f),
            new Vector3f(-3.8f, -2.0f, -12.3f),
            new Vector3f( 2.4f, -0.4f, -3.5f),
            new Vector3f(-1.7f,  3.0f, -7.5f),
            new Vector3f( 1.3f, -2.0f, -2.5f),
            new Vector3f( 1.5f,  2.0f, -2.5f),
            new Vector3f( 1.5f,  0.2f, -1.5f),
            new Vector3f(-1.3f,  1.0f, -1.5f)
    };

    //floor
    private static final float[] floor = {
            // positions         // colors           // texture coords
             0.5f, 0.0f, -0.5f,  1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
             0.5f, 0.0f,  0.5f,  0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
            -0.5f, 0.0f,  0.5f,  0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
            -0.5f, 0.0f, -0.5f,  1.0f, 1.0f, 0.0f,   0.0f, 1.0f,   // top left
    };

    private static final int[] floorIndices = {
            0, 1, 3,
            1, 2, 3,
    };

    private static final Vector3f floorPosition = new Vector3f(0.0f, -2.2f,0.0f);

    public static float[] getCube() {
        return cube;
    }

    public static int[] getCubeIndices() {
        return cubeIndices;
    }

    public static Vector3f[] getCubePositions() {
        return cubePositions;
    }

    public static float[] getFloor() {
        return floor;
    }

    public static int[] getFloorIndices() {
        return floorIndices;
    }

    public static Vector3f getFloorPosition() {
        return floorPosition;
    }
}
