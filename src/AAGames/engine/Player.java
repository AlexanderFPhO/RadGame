package src.AAGames.engine;

import src.AAGames.engine.graph.Renderer;
import src.AAGames.engine.scene.Camera;
import src.AAGames.engine.scene.Entity;
import src.AAGames.engine.scene.Scene;

public class Player {
    private Camera playerCam;
    private Entity playerEntity;

    public Player() {
    }

    public void init(Window window, Scene scene, Renderer renderer) {
        playerCam = scene.getCamera();
        playerEntity = new Entity();
    }

    public void move(float dX, float dY, float dZ) {
        playerCam.move();
    }

    public Camera getPlayerCam() {
        return playerCam;
    }

}
