package src.AAGames.engine.scene;

import src.AAGames.engine.IGuiInstance;
import src.AAGames.engine.graph.Model;
import src.AAGames.engine.graph.TextureCache;

import java.util.*;

public class Scene {
    private Camera camera;

    private Map<String, Model> modelMap;
    private Projection projection;
    private TextureCache textureCache;
    private IGuiInstance guiInstance;


    public Scene(int width, int height) {
        modelMap = new HashMap<>();
        textureCache = new TextureCache();
        projection = new Projection(width, height);
        camera = new Camera();


    }

    public void addEntity(Entity entity) {
        String modelId = entity.getModelId();
        Model model = modelMap.get(modelId);
        if (model == null) {
            throw new RuntimeException("Could not find model [" + modelId + "]");
        }
        model.getEntitiesList().add(entity);
    }

    public void addModel(Model model) {
        modelMap.put(model.getId(), model);
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }
    public void cleanup() {
        modelMap.values().forEach(Model::cleanup);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public Projection getProjection() {
        return projection;
    }

    public Camera getCamera() {
        return camera;
    }
    public IGuiInstance getGuiInstance() {
        return guiInstance;
    }
    public void resize(int width, int height) {
        projection.updateProjMatrix(width, height);
    }

    public void setGuiInstance(IGuiInstance guiInstance) {
        this.guiInstance = guiInstance;
    }
}