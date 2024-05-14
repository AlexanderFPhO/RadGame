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

        //ArrayList<int[]> chunkIDs = FileHandler.worldReader("C:\\Users\\LOFER\\IdeaProjects\\RadGame\\worlds\\dG93YXJk\\world.json");//"./world/" + worldID+"/world.json");


        /*for (int[] id : chunkIDs) {
            Chunk chunkObj = new Chunk(worldID, id);
            chunkObj.init(scene);
            loaded_chunks.add(chunkObj);
        }*/

        Model grassModel = ModelLoader.loadModel("cube-model", "resources/models/cube/cube.obj", scene.getTextureCache());
        scene.addModel(grassModel);
        for (int i = -16; i < 16; i++) {
            for (int j = -16; j < 16; j++) {
                cubeEntity = new Entity("cube-entity"+i+","+j, grassModel.getId());
                cubeEntity.setPosition(i, 2, -2+j);
                cubeEntity.setRotation(0,1,0, (float)(((int)(3*Math.random())) * Math.PI)/2f);
                cubeEntity.updateModelMatrix();
                scene.addEntity(cubeEntity);
            }
        }


        /*Model woodModel = ModelLoader.loadModel("wood-model", "resources/models/wood/wood.obj", scene.getTextureCache());
        scene.addModel(woodModel);
        woodEntity = new Entity("wood-entity", woodModel.getId());
        woodEntity.setPosition(0, 2, -2);
        woodEntity.updateModelMatrix();
        scene.addEntity(woodEntity);*/




    }
    public void update(Window window, Scene scene, long diffTimeMillis) {
        /*for (Chunk c : loaded_chunks)
        {
            c.update(window, scene, diffTimeMillis);
        }*/
    }

}
