package src.AAGames.game;

import imgui.*;
import imgui.flag.ImGuiCond;
import org.joml.Vector2f;
import src.AAGames.engine.*;
import src.AAGames.engine.graph.*;
import src.AAGames.engine.scene.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IGameLogic, IGuiInstance {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;

    private World gameWorld;
    private Player player;

    public static void main(String[] args) {
        Main main = new Main();
        Engine gameEng = new Engine("RadGame", new Window.WindowOptions(), main);
        gameEng.start();
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void drawGui() {
        ImGui.newFrame();
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);
        ImGui.showDemoWindow();
        ImGui.endFrame();
        ImGui.render();
    }

    @Override
    public boolean handleGuiInput(Scene scene, Window window) {
        ImGuiIO imGuiIO = ImGui.getIO();
        MouseInput mouseInput = window.getMouseInput();
        Vector2f mousePos = mouseInput.getCurrentPos();
        imGuiIO.setMousePos(mousePos.x, mousePos.y);
        imGuiIO.setMouseDown(0, mouseInput.isLeftButtonPressed());
        imGuiIO.setMouseDown(1, mouseInput.isRightButtonPressed());

        return imGuiIO.getWantCaptureMouse() || imGuiIO.getWantCaptureKeyboard();
    }

    @Override
    public void init(Window window, Scene scene, Renderer render) {
        gameWorld = new World();
        gameWorld.init(window, scene, render);

        player = new Player();//TODO: Add player constructor that accepts coordinates
        player.init(window, scene, render);

        scene.setGuiInstance(this);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        if (inputConsumed) {
            return;
        }
        //TODO: Add a menu to edit a JSON that specifies controls
        int c = 0;
        int[] deltaCameraPos = new int[3];

        if (window.isKeyPressed(GLFW_KEY_W)) {
            deltaCameraPos[2] = -1; c++;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            deltaCameraPos[2] = +1; c++;
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            deltaCameraPos[0] = -1; c++;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            deltaCameraPos[0] = +1; c++;
        }

        if (window.isKeyPressed(GLFW_KEY_E)) {
            deltaCameraPos[1] = 1; c++;
            player.isFlying = 0;


        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            //deltaCameraPos[1] = -1; c++;
            player.isFlying = 1;
        }

        if (c > 0) {
            float move = diffTimeMillis * MOVEMENT_SPEED / c;
            player.move(deltaCameraPos[0] * move, deltaCameraPos[1] * move, deltaCameraPos[2] * move);
        }

        MouseInput mouseInput = window.getMouseInput();
        Vector2f displVec = mouseInput.getDisplVec();
        if (mouseInput.isRightButtonPressed()) { //TODO: find a way to snap the mouse or camera to the center of the screen
            player.getPlayerCam().rotate((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        gameWorld.update(window, scene, diffTimeMillis);
        player.update(window, scene, diffTimeMillis);
        //System.out.println("FPS:" + 1000f/diffTimeMillis);
    }
}