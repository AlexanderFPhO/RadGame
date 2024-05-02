package src.AAGames.engine.graph;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class TextureCache {

    public static final String DEFAULT_TEXTURE = "resources/models/default/default_texture.png";

    private Map<String, Texture> textureMap;

    public TextureCache() {
        textureMap = new HashMap<>();
        textureMap.put(DEFAULT_TEXTURE, new Texture(DEFAULT_TEXTURE));
    }

    public void cleanup() {
        textureMap.values().forEach(Texture::cleanup);
    }

    public Texture createTexture(String texturePath) {
        return textureMap.computeIfAbsent(texturePath, Texture::new);
    }

    public Texture createEmbeddedTexture(String textureId, ByteBuffer textureData, int width, int height) {
        return textureMap.computeIfAbsent(textureId, key -> new Texture(textureData, width, height));
    }

    public Texture getTexture(String texturePath) {
        Texture texture = textureMap.getOrDefault(texturePath, textureMap.get(DEFAULT_TEXTURE));
        return texture;
    }
}
