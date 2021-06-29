package firstOpenGLGame.main.java.jade;

import imgui.ImGui;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Component {
    public transient GameObject gameObject = null;
    public void start() {}
    public abstract void update(float dt);
    public void imGui() {
        try {
            // getDeclaredFields() returns all fields, but getFields() returns only public ones
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isTransient(field.getModifiers())) continue;
                boolean isProvate = Modifier.isPrivate(field.getModifiers());
                if (isProvate) field.setAccessible(true);

                Class<?> type = field.getType();
                Object value = field.get(this);
                String name = field.getName();

                if (type == int.class) {
                    int[] val = {(int) value};
                    if (ImGui.dragInt(name, val)) field.set(this, val[0]);
                } else if (type == float.class) {
                    float[] val = {(float) value};
                    if (ImGui.dragFloat(name, val)) field.set(this, val[0]);
                } else if (type == boolean.class) {
                    boolean val = (boolean) value;
                    if (ImGui.checkbox(name, val)) field.set(this, !val);
                } else if (type == Vector3f.class) {
                    Vector3f vector3f = (Vector3f) value;
                    float[] val = {vector3f.x, vector3f.y, vector3f.z};
                    if (ImGui.dragFloat3(name, val)) vector3f.set(val[0], val[1], val[2]);
                } else if (type == Vector4f.class) {
                    Vector4f vector4f = (Vector4f) value;
                    float[] val = {vector4f.x, vector4f.y, vector4f.z, vector4f.w};
                    if (ImGui.dragFloat4(name, val)) vector4f.set(val[0], val[1], val[2], val[3]);
                }

                if (isProvate) field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
