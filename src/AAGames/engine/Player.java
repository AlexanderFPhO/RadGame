package src.AAGames.engine;

import src.AAGames.engine.graph.Model;
import src.AAGames.engine.graph.Renderer;
import src.AAGames.engine.scene.Camera;
import src.AAGames.engine.scene.Entity;
import src.AAGames.engine.scene.ModelLoader;
import src.AAGames.engine.scene.Scene;

public class Player {
    private Camera playerCam;
    private Entity playerEntity;

    public float[] position;
    public float[] rotation;
    public float[] velocity;
    public int isFlying;

    public Player() {}

    public void init(Window window, Scene scene, Renderer renderer) {
        playerCam = scene.getCamera();
        isFlying = 0;

        Model playerModel = ModelLoader.loadModel("player-model", "resources/models/player/player.obj", scene.getTextureCache());
        scene.addModel(playerModel);
        playerEntity = new Entity("player-entity", playerModel.getId());
        playerEntity.setPosition(0f, 0f, 0f);
        position = new float[3];
        rotation = new float[3];
        velocity = new float[3];
        playerEntity.setScale(1f/8);
        playerEntity.updateModelMatrix();
        scene.addEntity(playerEntity);
    }

    public void move(float dX, float dY, float dZ) {
        playerCam.move(dX, dY, dZ);
        position[0] += dX; position[1] += dY; position[2] += dZ;
        playerEntity.setPosition(position[0], position[1], position[2]);
        playerEntity.updateModelMatrix();

    }

    public void rotate( float dXRot, float dYRot) {//TODO: quaternions!
        playerCam.rotate(dXRot, dYRot);

        rotation[0] += dXRot; rotation[1] += dYRot;
        //playerEntity.rotate(rotation[0], rotation[1]);
        //playerEntity.updateModelMatrix();
    }

    public Camera getPlayerCam() {
        return playerCam;
    }

    public void update(Window window, Scene scene, long diffTimeMillis) {
        this.move(velocity[0]*diffTimeMillis, velocity[1]*diffTimeMillis, velocity[2]*diffTimeMillis);
        velocity[1] += -.01f*(diffTimeMillis/1000f)*(isFlying);
        System.out.println(velocity[1]);
    }
}
