package src.AAGames.engine.scene;

import org.joml.*;

public class Camera {

    private Vector3f direction;
    private Vector3f position;
    private Vector3f right;
    private Vector2f rotation;
    private Vector3f up;
    private Matrix4f viewMatrix;
    private Matrix4f invViewMatrix;


    public Camera() {
        direction = new Vector3f();
        right = new Vector3f();
        up = new Vector3f();
        position = new Vector3f(0f, 2f, 0f);
        viewMatrix = new Matrix4f();
        invViewMatrix = new Matrix4f();
        rotation = new Vector2f();
    }

    public void rotate(float x, float y) {
        rotation.add(x, y);
        recalculate();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getInvViewMatrix() {
        return invViewMatrix;
    }

    public void move(float incX, float incY, float incZ, Matrix3f playerRotationMatrix) {
        // Transform the increments to the player's local coordinates
        Vector3f localIncrements = playerRotationMatrix.transform(new Vector3f(incX, incY, incZ));

        // Update the camera's position based on the local increments
        viewMatrix.positiveX(right).mul(localIncrements.x);
        viewMatrix.positiveY(up).mul(localIncrements.y);
        viewMatrix.positiveZ(direction).mul(localIncrements.z);
        position.add(right).add(up).add(direction);
        recalculate();
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        recalculate();
    }

    public void setRotation(float x, float y) {
        rotation.set(x, y);
        recalculate();
    }

    private void recalculate() {
        viewMatrix.identity()
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .translate(-position.x, -position.y, -position.z);
        invViewMatrix.set(viewMatrix).invert();

    }
}