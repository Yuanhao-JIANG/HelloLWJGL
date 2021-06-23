package firstOpenGLGame.main.java.jade;

public abstract class Component {
    public transient GameObject gameObject = null;
    public void start() {}
    public abstract void update(float dt);
    public void imGui() {}
}
