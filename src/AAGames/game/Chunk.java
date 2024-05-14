package src.AAGames.game;

import src.AAGames.engine.FileHandler;
import src.AAGames.engine.Mob;
import src.AAGames.engine.Window;
import src.AAGames.engine.SerializedBlock;
//import src.AAGames.engine.SerializedMob;
import src.AAGames.engine.graph.Model;
import src.AAGames.engine.scene.Entity;
import src.AAGames.engine.scene.ModelLoader;
import src.AAGames.engine.scene.Scene;

import java.util.ArrayList;
import java.util.TreeMap;

public class Chunk {
    int[] id;
    String worldID;
    ArrayList<Entity> mobs;
    ArrayList<Entity> blocks;
    TreeMap<String, Model> modelMap;


    public Chunk(String worldID, int[] id) {
        this.id = id;
        this.worldID = worldID;
    }

    public void init(Scene scene) {
        //ArrayList<SerializedMob> sMobs = FileHandler.chunkMobReader("./world/" + worldID+"/"+id[0]+","+id[1]+".json");
        ArrayList<SerializedBlock> sBlocks = FileHandler.chunkBlockReader("./world/" + worldID+"/"+id[0]+","+id[1]+".json");

        //for (SerializedMob m : sMobs) {

        //}
        for (SerializedBlock b : sBlocks) {
            String blockType = b.blockType();
            if (!modelMap.containsKey(b.blockType())) {
                Model tempModel = ModelLoader.loadModel(blockType+"-model", "resources/models/"+blockType+"/"+blockType+".obj", scene.getTextureCache());
                scene.addModel(tempModel);
                modelMap.put(blockType, tempModel);
            }
            int[] pos = b.position();

            Entity tempEntity = new Entity(blockType+"-block-"+pos[0]+"-"+pos[1]+"-"+pos[2], modelMap.get(blockType).getId());
            tempEntity.setPosition(pos[0], pos[1], pos[2]);
            tempEntity.updateModelMatrix();
            scene.addEntity(tempEntity);

        }

    }

    public void update(Window window, Scene scene, long diffTimeMillis) {
        for (Entity e: mobs) {
            e.getMob().update(diffTimeMillis);
        }
    }


}