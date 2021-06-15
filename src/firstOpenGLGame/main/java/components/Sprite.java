package firstOpenGLGame.main.java.components;

import firstOpenGLGame.main.java.jade.Component;
import firstOpenGLGame.main.java.jade.Transform;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Arrays;

public class Sprite extends Component {
    private Vector4f color;
    // the default textureID is 0 so that if it is 0 then there is no texture bind to this sprite
    private int textureID;
    private Vector2f[] texCoords = new Vector2f[] {
            new Vector2f(0.0f, 1.0f),
            new Vector2f(1.0f, 1.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(0.0f, 0.0f)
    };
    private Transform lastTransform;
    private boolean isDirty = true;

    public Sprite(Vector4f color) {
        this.color = color;
    }

    public Sprite(int textureID) {
        this.textureID = textureID;
        this.texCoords = new Vector2f[] {
                new Vector2f(0.0f, 1.0f),
                new Vector2f(1.0f, 1.0f),
                new Vector2f(1.0f, 0.0f),
                new Vector2f(0.0f, 0.0f)
        };
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Sprite(int textureID, Vector2f[] texCoords) {
        this.textureID = textureID;
        this.texCoords = texCoords;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Sprite(Sprite sprite) {
        this.textureID = sprite.getTextureID();
        this.texCoords = sprite.getTexCoords();
        this.color = sprite.getColor();
        this.isDirty = sprite.isDirty();
        this.lastTransform = sprite.lastTransform;
    }

    @Override
    public void start() {
        lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if (!lastTransform.equals(gameObject.transform)) {
            gameObject.transform.copyTo(lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imGui() {
        float[] imGuiColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color Picker", imGuiColor)) {
            color.set(imGuiColor[0], imGuiColor[1], imGuiColor[2], imGuiColor[3]);
            isDirty = true;
        }
    }

    public void reset(Sprite sprite) {
        setTexCoords(sprite.getTexCoords());
        setTextureID(sprite.getTextureID());
        setColor(sprite.getColor());
    }

    public void setTexCoords(Vector2f[] texCoords) {
        if (!Arrays.equals(this.texCoords, texCoords)) {
            this.texCoords = texCoords;
            isDirty = true;
        }
    }

    public void setTextureID(int textureID) {
        if (!(this.textureID == textureID)) {
            this.textureID = textureID;
            isDirty = true;
        }
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.color = color;
            isDirty = true;
        }
    }

    public Vector4f getColor() {
        return color;
    }

    public int getTextureID() {
        return textureID;
    }

    public Vector2f[] getTexCoords() {
        return texCoords;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setClean() {
        isDirty = false;
    }
}
