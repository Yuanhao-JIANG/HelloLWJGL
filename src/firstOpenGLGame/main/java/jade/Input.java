package firstOpenGLGame.main.java.jade;

import imgui.ImGui;
import imgui.ImGuiIO;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

//General and ImGui keyboard, mouse, cursor IO
//Other ImGui IO are inside ImGuiLayer.java
public class Input {
    private static final boolean[] keyStatus = new boolean[GLFW_KEY_LAST];
    private static final boolean[] mouseButtonStatus = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static boolean isMouseInitiated;
    private static float mouseX, mouseY, lastX, lastY, xOffset, yOffset;
    private static float scrollXOffset, scrollYOffset;

    private static final GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            keyStatus[key] = action != GLFW_RELEASE;

            //ImGui configuration
            final ImGuiIO io = ImGui.getIO();
            io.setKeysDown(key, action != GLFW_RELEASE);

            io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
            io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
            io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
            io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
        }
    };
    private static final GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            mouseButtonStatus[button] = action != GLFW_RELEASE;

            //ImGui configuration
            final ImGuiIO io = ImGui.getIO();
            io.setMouseDown(button, action != GLFW_RELEASE);
        }
    };
    private static final GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            mouseX = (float) xpos;
            mouseY = (float) ypos;
        }
    };
    private static final GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
        @Override
        public void invoke(long window, double xoffset, double yoffset) {
            scrollXOffset = (float) xoffset;
            scrollYOffset = (float) yoffset;

            //ImGui configuration
            final ImGuiIO io = ImGui.getIO();
            io.setMouseWheelH(io.getMouseWheelH() + (float) xoffset);
            io.setMouseWheel(io.getMouseWheel() + (float) yoffset);
        }
    };
    private static final GLFWCharCallback charCallback = new GLFWCharCallback() {
        @Override
        public void invoke(long window, int codepoint) {
            final ImGuiIO io = ImGui.getIO();
            io.addInputCharacter(codepoint);
        }
    };

    public static boolean isKeyDown(int key) {
        return keyStatus[key];
    }

    public static boolean isMouseButtonDown(int mouseButton) {
        return mouseButtonStatus[mouseButton];
    }

    public static boolean isDragging() {
        return isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT);
    }
    public static void calculateOffset() {
        if (!isMouseInitiated && (getMouseX() != 0|| getMouseY() != 0)) {
            lastX = getMouseX();
            lastY = getMouseY();
            isMouseInitiated = true;
        }
        xOffset = getMouseX() - lastX;
        yOffset = lastY - getMouseY();
        lastX = getMouseX();
        lastY = getMouseY();
    }

    public static float getXOffset() {
        return xOffset;
    }

    public static float getYOffset() {
        return yOffset;
    }

    public static float getMouseX() {
        return mouseX;
    }

    public static float getMouseY() {
        return mouseY;
    }

    public static float getScrollXOffset() {
        return scrollXOffset;
    }

    public static float getScrollYOffset() {
        float yOffset = scrollYOffset;
        scrollYOffset = 0;
        return yOffset;
    }

    public static GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public static GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public static GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public static GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public static GLFWCharCallback getCharCallback() {
        return charCallback;
    }
}
