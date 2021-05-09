package firstOpenGLGame.main.java.components;

import firstOpenGLGame.main.java.jade.Component;

public class SpriteRenderer extends Component {
    private boolean isUpdating = false;
    @Override
    public void start() {
        System.out.println("Start sprite renderer.");
    }
    @Override
    public void update(float dt) {
        if (!isUpdating) {
            System.out.println("Start to update spriteRenderer.");
            isUpdating = true;
        }

    }
}
