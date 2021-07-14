package firstOpenGLGame.main.java.components;

public class FontRenderer extends Component {
    private boolean isUpdating = false;
    @Override
    public void start() {
        if (gameObject.getComponent(Sprite.class) != null) {
            System.out.println("Start font renderer.");
        }
    }
    @Override
    public void update(float dt) {
        if (!isUpdating) {
            System.out.println("Start to update font renderer.");
            isUpdating = true;
        }
    }
}
