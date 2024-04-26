package src.AAGames.engine.graph;

import org.lwjgl.opengl.GL;
import src.AAGames.engine.Window;
import src.AAGames.engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private SceneRenderer sceneRenderer;
    private GuiRenderer guiRenderer;
    private SkyBoxRender skyBoxRender;


    public Renderer(Window window) {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        sceneRenderer = new SceneRenderer();
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        guiRenderer = new GuiRenderer(window);
        skyBoxRender = new SkyBoxRender();

    }

    public void cleanup() {
        sceneRenderer.cleanup();
        guiRenderer.cleanup();}

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getWidth(), window.getHeight());

        sceneRenderer.render(scene);
        guiRenderer.render(scene);
        skyBoxRender.render(scene);
    }
    public void resize(int width, int height) {
        guiRenderer.resize(width, height);
    }
}