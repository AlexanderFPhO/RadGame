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

    ArrayList<Entity> mobs;
    private Entity cubeEntity;
    private Entity wood2Entity;
    private Entity woodEntity;

    public void init(Window window, Scene scene, Renderer render) {
        this.mobs = new ArrayList<Entity>();
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
        for (int i = mobs.size() - 1; i >= 0; i--) {
            Entity e = mobs.get(i);
            Mob mob = e.getMob();
            mob.update(diffTimeMillis);
            if (mob.health <= 0) {
                mobs.remove(i);
                scene.removeEntity(e);
            }
        }
        /*for (Chunk c : loaded_chunks)
        {
            c.update(window, scene, diffTimeMillis);
        }*/
    }

    public void loadLevel1(Window window, Scene scene) {
        Model woodModel = ModelLoader.loadModel("enemy-model", "resources/models/green-enemy/cube.obj", scene.getTextureCache());
        scene.addModel(woodModel);
        woodEntity = new Entity("enemy-entity1", woodModel.getId());
        woodEntity.setPosition(8, 3, 0);
        woodEntity.setRotation(1, 0, 0, 3.1459f/2);
        woodEntity.updateModelMatrix();
        woodEntity.setMob(new Mob(new float[]{8, 3,0}, new float[]{0, 0, 0}, new float[]{0, 0, 0}, 1, 10));
        scene.addEntity(woodEntity);
        mobs.add(woodEntity);

        //Model woodModel = ModelLoader.loadModel("enemy-model",  "resources/models/wood2/wood2.obj", scene.getTextureCache());
        //scene.addModel(woodModel);
        woodEntity = new Entity("enemy-entity2", woodModel.getId());
        woodEntity.setPosition(0, 3, -8);
        woodEntity.setRotation(1, 0, 0, 3.1459f/2);

        woodEntity.updateModelMatrix();
        woodEntity.setMob(new Mob(new float[]{0, 3, -8}, new float[]{0, 0, 0}, new float[]{0, 0, 0}, 1, 10));
        scene.addEntity(woodEntity);
        mobs.add(woodEntity);

        //Model woodModel = ModelLoader.loadModel("enemy-model", "resources/models/wood2/wood2.obj", scene.getTextureCache());
        //scene.addModel(woodModel);
        woodEntity = new Entity("enemy-entity3", woodModel.getId());
        woodEntity.setPosition(-8, 3, 0);
        woodEntity.setRotation(1, 0, 0, 3.1459f/2);

        woodEntity.updateModelMatrix();
        woodEntity.setMob(new Mob(new float[]{-8, 3, 0}, new float[]{0, 0, 0}, new float[]{0, 0, 0}, 1, 10));
        scene.addEntity(woodEntity);
        mobs.add(woodEntity);

        //Model woodModel = ModelLoader.loadModel("enemy-model", "resources/models/wood2/wood2.obj", scene.getTextureCache());
        //scene.addModel(woodModel);
        woodEntity = new Entity("enemy-entity4", woodModel.getId());
        woodEntity.setPosition(0, 3, 8);
        woodEntity.setRotation(1, 0, 0, 3.1459f/2);

        woodEntity.updateModelMatrix();
        woodEntity.setMob(new Mob(new float[]{0, 3, 8}, new float[]{0, 0, 0}, new float[]{0, 0, 0}, 1, 10));
        scene.addEntity(woodEntity);
        mobs.add(woodEntity);

    }

    public void loadLevel2(Window window, Scene scene) {
    }
}
