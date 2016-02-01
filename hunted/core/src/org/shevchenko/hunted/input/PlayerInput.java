package org.shevchenko.hunted.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import org.shevchenko.hunted.generators.PlayerGenerator;

public class PlayerInput implements InputProcessor {

    private PlayerGenerator player;
    private float force = 0.05f;

    public MoveType type;

    public PlayerInput(PlayerGenerator player) {
        this.player = player;
    }

    public PlayerInput(MoveType type) {
        this.type = type;
        type = MoveType.STAY;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {

            case Keys.W:
                type = MoveType.UP;
                break;
            case Keys.S:
                type = MoveType.DOWN;
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keywcode) {
        switch (keywcode) {
            case Keys.W:
                type = MoveType.STAY;
                break;
            case Keys.S:
                type = MoveType.STAY;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    public void update() {
        if (type != null)
            switch (type) {
                case UP:
                    player.getEntity().transform.translate(new Vector3(0, -force, 0));
                    break;
                case DOWN:
                    player.getEntity().transform.translate(new Vector3(0, force, 0));
                    break;
                case STAY:
                    player.getEntity().transform.translate(new Vector3(0, 0, 0));
                    break;
            }
    }

    public void cameraToPlayer(PerspectiveCamera camera) {
        Vector3 tmp = new Vector3(player.getPosition());
        camera.translate(Vector3.Zero);
    }

}
