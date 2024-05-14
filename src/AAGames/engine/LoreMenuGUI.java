package src.AAGames.engine;

import imgui.ImGui;
import src.AAGames.engine.scene.Scene;

public class LoreMenuGUI implements IGuiInstance{

    @Override
    public void drawGui() {
        ImGui.text("hello world");
        ImGui.end();
    }

    @Override
    public boolean handleGuiInput(Scene scene, Window window) {
        return false;
    }
}
