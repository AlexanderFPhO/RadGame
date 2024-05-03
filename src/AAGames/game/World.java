package src.AAGames.game;

import src.AAGames.engine.*;
import src.AAGames.engine.graph.*;
import src.AAGames.engine.scene.*;
import src.AAGames.game.Chunk;
import src.AAGames.engine.FileHandler;

import java.io.File;
import java.util.ArrayList;

import static src.AAGames.engine.FileHandler.*;

public class World {
    private float rotation;

    private ArrayList<Chunk> loaded_chunks;
    private ArrayList<int[]> world_chunks;
    private Entity cubeEntity;
    private Entity wood2Entity;
    private Entity woodEntity;

    public void init(Window window, Scene scene, Renderer render) {
        String worldID = "dG93YXJk";
        //TODO: Fix memory hogging (one model is 6mb, 14mb per entity
        Model grassModel = ModelLoader.loadModel("cube-model", "resources/models/cube/cube.obj", scene.getTextureCache());
        scene.addModel(grassModel);
        cubeEntity = new Entity("cube-entity", grassModel.getId());
        cubeEntity.setPosition(1, 2, -2);
        cubeEntity.updateModelMatrix();
        scene.addEntity(cubeEntity);

        Model wood2Model = ModelLoader.loadModel("wood2-model", "resources/models/wood2/wood2.obj", scene.getTextureCache());
        scene.addModel(wood2Model);
        wood2Entity = new Entity("wood2-entity", wood2Model.getId());
        wood2Entity.setPosition(2, 2, -2);
        wood2Entity.updateModelMatrix();
        scene.addEntity(wood2Entity);

        Model woodModel = ModelLoader.loadModel("wood-model", "resources/models/wood/wood.obj", scene.getTextureCache());
        scene.addModel(woodModel);
        woodEntity = new Entity("wood-entity", woodModel.getId());
        woodEntity.setPosition(0, 2, -2);
        woodEntity.updateModelMatrix();
        scene.addEntity(woodEntity);

        //ArrayList<int[]> chunkIDs = FileHandler.worldReader("./world/" + worldID+"/world.json");

        //for (int[] id : chunkIDs) {
        //    Chunk chunkObj = new Chunk(worldID, id);
        //    chunkObj.init();
        //    loaded_chunks.add(chunkObj);
        //}


    }
    public void update(Window window, Scene scene, long diffTimeMillis) {
        /*for (Chunk c : loaded_chunks)
        {
            c.update(window, scene, diffTimeMillis);
        }*/
    }

}
