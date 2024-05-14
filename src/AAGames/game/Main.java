package src.AAGames.game;

import imgui.*;
import imgui.flag.ImGuiCond;
import org.joml.*;
import src.AAGames.engine.*;
import src.AAGames.engine.graph.*;
import src.AAGames.engine.scene.*;

import java.lang.Math;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IGameLogic, IGuiInstance {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.008f;

    private int level;
    private World gameWorld;
    private Player player;

    private Window window;

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
        ImGui.setNextWindowPos(this.window.getWidth()/2-300, this.window.getHeight()/2-300, ImGuiCond.Always);
        ImGui.setNextWindowSize(800, 250);
        if (level == -1) {
            glfwSetInputMode(this.window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            ImGui.begin("Level Selector", 2+8+4);
            ImGui.text("Pick a level!");
            if (ImGui.button("Level 1", 200, 200)) {level = 1;};
            ImGui.sameLine();
            if (ImGui.button("Level 2", 200, 200)) {level = 2;};
            ImGui.sameLine();
            if (ImGui.button("Freeroam", 200, 200)) {level = 0;};

            ImGui.end();
        } else {
            ImGui.progressBar(this.player.health/20f);
            glfwSetInputMode(this.window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        }
        //ImGui.showDemoWindow();
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
        this.level = -1;
        this.window = window;
        gameWorld = new World();
        gameWorld.init(window, scene, render);

        glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        player = new Player();//TODO: Add player constructor that accepts coordinates
        player.init(window, scene, render);

        scene.setGuiInstance(this);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        if (inputConsumed) {
            return;
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isLeftButtonPressed()) {
            selectEntity(window, scene, mouseInput.getCurrentPos());
        }

        //TODO: Add a menu to edit a JSON that specifies controls
        int c = 0;
        int[] deltaCameraPos = new int[3];

        if (window.isKeyPressed(GLFW_KEY_W)) {
            deltaCameraPos[2] = -1;
            c++;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            deltaCameraPos[2] = +1;
            c++;
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            deltaCameraPos[0] = -1;
            c++;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            deltaCameraPos[0] = +1;
            c++;
        }

        if (window.isKeyPressed(GLFW_KEY_SPACE) && player.isFlying == 0) {
            deltaCameraPos[1] = 1;
            c++;
            player.isFlying = 1;
            player.velocity[1] = 0.016f;

        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            //deltaCameraPos[1] = -1; c++;
            //player.isFlying = 1;
        }

        if (window.isKeyPressed(GLFW_KEY_T)) {
            player.rotate(0, .1f);
        } else if (window.isKeyPressed(GLFW_KEY_Y)) {
            player.rotate(0, -.1f);
        }

        if (c > 0) {
            float norm = diffTimeMillis * MOVEMENT_SPEED;
            Matrix3f rotationMatrix = new Matrix3f().rotateXYZ(player.rotation[0], player.rotation[1], player.rotation[2]);

            Vector3f globalIncrements = new Vector3f(deltaCameraPos[0] * norm, deltaCameraPos[1] * norm, deltaCameraPos[2] * norm);
            globalIncrements.normalize(norm);

            Matrix4f invViewMatrix = player.getPlayerCam().getInvViewMatrix();

            Vector4f localIncrements = new Vector4f(globalIncrements, 0.0f).mul(invViewMatrix);

            player.move(new Vector3f(localIncrements.x, 0, localIncrements.z), rotationMatrix);



        }


        Vector2f displVec = mouseInput.getDisplVec();
        player.rotate((float) Math.toRadians(displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(displVec.y * MOUSE_SENSITIVITY));
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        gameWorld.update(window, scene, diffTimeMillis);
        player.update(window, scene, diffTimeMillis);

        //System.out.println("FPS:" + 1000f/diffTimeMillis);
    }

    private Entity selectEntity(Window window, Scene scene, Vector2f mousePos) {
        int wdwWidth = window.getWidth();
        int wdwHeight = window.getHeight();

        float x = (2 * mousePos.x) / wdwWidth - 1.0f;
        float y = 1.0f - (2 * mousePos.y) / wdwHeight;
        float z = -1.0f;

        Matrix4f invProjMatrix = scene.getProjection().getInvProjMatrix();
        Vector4f mouseDir = new Vector4f(x, y, z, 1.0f);
        mouseDir.mul(invProjMatrix);
        mouseDir.z = -1.0f;
        mouseDir.w = 0.0f;

        Matrix4f invViewMatrix = scene.getCamera().getInvViewMatrix();
        mouseDir.mul(invViewMatrix);

        Vector4f min = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        Vector4f max = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        Vector2f nearFar = new Vector2f();

        Entity selectedEntity = null;
        float closestDistance = Float.POSITIVE_INFINITY;
        Vector3f center = scene.getCamera().getPosition();

        Collection<Model> models = scene.getModelMap().values();
        Matrix4f modelMatrix = new Matrix4f();
        for (Model model : models) {
            List<Entity> entities = model.getEntitiesList();
            for (Entity entity : entities) {
                modelMatrix.translate(entity.getPosition()).scale(entity.getScale());
                for (Material material : model.getMaterialList()) {
                    for (Mesh mesh : material.getMeshList()) {
                        Vector3f aabbMin = mesh.getAabbMin();
                        min.set(aabbMin.x, aabbMin.y, aabbMin.z, 1.0f);
                        min.mul(modelMatrix);
                        Vector3f aabMax = mesh.getAabbMax();
                        max.set(aabMax.x, aabMax.y, aabMax.z, 1.0f);
                        max.mul(modelMatrix);
                        if (Intersectionf.intersectRayAab(center.x, center.y, center.z, mouseDir.x, mouseDir.y, mouseDir.z,
                                min.x, min.y, min.z, max.x, max.y, max.z, nearFar) && nearFar.x < closestDistance && !entity.getId().equals("player-entity")) {
                            closestDistance = nearFar.x;
                            selectedEntity = entity;
                        }
                    }
                }
                modelMatrix.identity();
            }
        }
        if (selectedEntity != null && selectedEntity.getId().contains("enemy") && closestDistance < 3) {
            selectedEntity.getMob().damageMob(player.attackDmg);
            System.out.println("Damaged: " + selectedEntity.getId());
        }
        return selectedEntity;
    }
}