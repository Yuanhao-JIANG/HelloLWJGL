package firstOpenGLGame.main.java.jade;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private String name;
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
}
