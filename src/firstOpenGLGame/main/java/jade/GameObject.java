package firstOpenGLGame.main.java.jade;

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

            // the following line of code is added for the reason that if the obj gets serialized, the gameObject field
            // of the components of this game object is not serialized (as we set the gameObject inside Component.java
            // transient), so it needs to be reset.
            if (component.gameObject != this) component.gameObject = this;

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
