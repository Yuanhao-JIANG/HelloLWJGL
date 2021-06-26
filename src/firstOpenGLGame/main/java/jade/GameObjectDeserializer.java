package firstOpenGLGame.main.java.jade;

import com.google.gson.*;

import java.lang.reflect.Type;

public class GameObjectDeserializer implements JsonDeserializer<GameObject> {
    @Override
    public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        JsonArray components = jsonObject.getAsJsonArray("components");
        Transform transform = context.deserialize(jsonObject.get("transform"), Transform.class);

        GameObject gameObject = new GameObject(name, transform);
        for (JsonElement e : components) {
            Component c = context.deserialize(e, Component.class);
            gameObject.addComponent(c);
        }

        return gameObject;
    }
}
