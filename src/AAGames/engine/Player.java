package src.AAGames.engine;

import org.joml.Matrix3f;
import org.joml.Vector3f;
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
    public short isFlying;

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


//Create a rotation matrix from the player's rotation
        Matrix3f rotationMatrix = new Matrix3f().rotateXYZ(rotation[0], rotation[1], rotation[2]);

        // Transform the increments to the player's local coordinates
        Vector3f localIncrements = rotationMatrix.transform(new Vector3f(dX, dY, dZ));

        // Update the player's position based on the local increments
        position[0] += localIncrements.x;
        position[1] += localIncrements.y;
        position[2] += localIncrements.z;

        playerEntity.setPosition(position[0], position[1], position[2]);
        playerEntity.updateModelMatrix();

        // Update the camera's position to follow the player
        playerCam.move(localIncrements.x, localIncrements.y, localIncrements.z, rotationMatrix);
    }

    public void rotate(float dXrot, float dYRot) {//TODO: quaternions!

        rotation[1] += dYRot;
        rotation[0] += dXrot;
        rotation[0] = (float) (Math.max(Math.min(rotation[0],Math.PI/2), -Math.PI/2));
        playerCam.setRotation(rotation[0], rotation[1]);

        playerEntity.setRotation(0,-1,0, rotation[1]);
        playerEntity.updateModelMatrix();
    }
    private static final Vector3f GRAVITY = new Vector3f(0, -.01f, 0);

    public Camera getPlayerCam() {
        return playerCam;
    }

    public void update(Window window, Scene scene, long diffTimeMillis) {
        // Create a rotation matrix from the player's rotation
        Matrix3f rotationMatrix = new Matrix3f().rotateXYZ(rotation[0], rotation[1], rotation[2]);

        // Transform the gravity vector to the player's local coordinates
        Vector3f localGravity = rotationMatrix.transform(new Vector3f(GRAVITY));

        // Update the player's velocity based on the local gravity
        //velocity[0] += localGravity.x * diffTimeMillis/1000f * isFlying;
        velocity[1] += localGravity.y * diffTimeMillis/1000f * isFlying;
        //velocity[2] += localGravity.z * diffTimeMillis/1000f * isFlying;

        this.move(velocity[0]*diffTimeMillis, velocity[1]*diffTimeMillis, velocity[2]*diffTimeMillis);
    }
}
