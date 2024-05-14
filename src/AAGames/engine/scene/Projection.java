package src.AAGames.engine.scene;

import org.joml.Matrix4f;

public class Projection {
    //TODO: Add setting FOV, Z_FAR, Z_NEAR from a JSON object
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_FAR = 1000.f;
    private static final float Z_NEAR = 0.01f;

    private Matrix4f projMatrix;
    private Matrix4f invProjMatrix;


    public Projection(int width, int height) {
        projMatrix = new Matrix4f();
        invProjMatrix = new Matrix4f();

        updateProjMatrix(width, height);
    }

    public Matrix4f getProjMatrix() {
        return projMatrix;
    }

    public Matrix4f getInvProjMatrix() {
        return invProjMatrix;
    }

    public void updateProjMatrix(int width, int height) {
        projMatrix.setPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
        invProjMatrix.set(projMatrix).invert();
    }
}