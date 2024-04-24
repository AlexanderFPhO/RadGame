package src.AAGames.game;

import src.AAGames.engine.*;
import src.AAGames.engine.graph.*;
import src.AAGames.engine.scene.*;

public class World {
    private float rotation;
    private Entity cubeEntity;

    public void init(Window window, Scene scene, Renderer render) {
        Model cubeModel = ModelLoader.loadModel("cube-model", "resources/models/cube/cube.obj", scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0, 2, -2);
        scene.addEntity(cubeEntity);
    }
    public void update(Window window, Scene scene, long diffTimeMillis) {

    }

}
