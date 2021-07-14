package firstOpenGLGame.main.java.jade;

import firstOpenGLGame.main.java.components.Component;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private final String name;
    private final List<Component> components;
    public Transform transform;

    public GameObject(String name) {
        this.name = name;
        components = new ArrayList<>();
        transform = new Transform();
    }

    public GameObject(String name, Transform transform) {
        this.name = name;
        components = new ArrayList<>();
        this.transform = transform;
    }

    public GameObject(String name, float posX, float posY, float z, float width, float height) {
        this.name = name;
        components = new ArrayList<>();
        this.transform = new Transform(new Vector3f(posX, posY, z), new Vector2f(width, height));
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                return componentClass.cast(c);
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            if (componentClass.isAssignableFrom(components.get(i).getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        components.add(c);
        c.gameObject = this;
    }

    public void update(float dt) {
        for (Component component : components) {
            component.update(dt);
        }
    }

    public void start() {
        for (Component component : components) {
            component.start();
        }
    }

    public void imGui() {
        for (Component c : components) {
            c.imGui();
        }
    }
}
