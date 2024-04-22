package src.AAGames.engine;

import src.AAGames.engine.scene.Scene;
import src.AAGames.engine.graph.Renderer;


public interface IGameLogic {

    void cleanup();

    void init(Window window, Scene scene, Renderer renderer);

    void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed);

    void update(Window window, Scene scene, long diffTimeMillis);
}