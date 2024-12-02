package com.project.use_cases.core.editor;

import com.project.interface_adapters.core.editor.EditorTileManager;
import com.project.use_cases.core.game.GameInteractor;
import com.project.entity.core.GameObject;
import com.project.entity.core.Scene;
import com.project.entity.core.Tuple;
import com.project.use_cases.core.game.GameOutputData;
import com.project.entity.input.EInputType;
import com.project.use_cases.player_move.PlayerMoveInputData;
import com.project.entity.scripting.IScriptable;

public class EditorCamera implements IScriptable {

    float moveSpeed = 400f;

    @Override
    public void start(GameObject parent) {
        parent.getLinkedScene().setScaleX(0.9);
        parent.getLinkedScene().setScaleY(0.9);
    }

    @Override
    public void update(GameObject parent, double deltaTime) {
        GameOutputData win = GameInteractor.getInstance().getPrimaryWindow();
        PlayerMoveInputData ic = GameInteractor.getInstance().getInputCache();
        if (win == null) {
            return;
        }

        if (ic.isKeyPressed("A") || ic.isKeyPressed("LEFT")) {
            parent.getTransform().translate(-moveSpeed*deltaTime, 0d);
        }
        if (ic.isKeyPressed("D") || ic.isKeyPressed("RIGHT")) {
            parent.getTransform().translate(moveSpeed*deltaTime, 0d);
        }
        if (ic.isKeyPressed("W") || ic.isKeyPressed("UP")) {
            parent.getTransform().translate(0d, -moveSpeed*deltaTime);
        }
        if (ic.isKeyPressed("S") || ic.isKeyPressed("DOWN")) {
            parent.getTransform().translate(0d, moveSpeed*deltaTime);
        }

        if(ic.isKeyPressed("SHIFT")) {
            modifyTile(parent, true);
        }
        else if (ic.isKeyPressed("CTRL")) {
            modifyTile(parent, false);
        }
    }

    private Tuple<Integer, Integer> getMousePos(GameObject camera, Scene scene, PlayerMoveInputData input) {
        double mouseX = input.getMouseX();
        double mouseY = input.getMouseY();

        double scaleX = scene.getScaleX();
        double scaleY = scene.getScaleY();

        double adjustedMouseX;
        double adjustedMouseY;

        adjustedMouseX = mouseX / scaleX + camera.getTransform().getPositionX();
        adjustedMouseY = mouseY / scaleY + camera.getTransform().getPositionY();

        int tileX = (int) Math.floor(adjustedMouseX / EditorTileManager.BASE_TILE_SIZE);
        int tileY = (int) Math.floor(adjustedMouseY / EditorTileManager.BASE_TILE_SIZE);

        int snappedX = tileX * EditorTileManager.BASE_TILE_SIZE;
        int snappedY = tileY * EditorTileManager.BASE_TILE_SIZE;

        return new Tuple<>(snappedX, snappedY);
    }


    private void modifyTile(GameObject parent, boolean add) {
        GameOutputData w = GameInteractor.getInstance().getPrimaryWindow();
        if (w == null) {
            return;
        }
        Scene s = parent.getLinkedScene();
        LevelEditor le = (LevelEditor) s;

        Tuple<Integer, Integer> mousePos = getMousePos(parent, s, GameInteractor.getInstance().getInputCache());

        if (add)
            le.addTile(le.selectedTileType, mousePos.getFirst(), mousePos.getSecond());
        else
            le.removeTile(mousePos.getFirst(), mousePos.getSecond());
    }

    @Override
    public void onInput(GameObject parent, String keyName, EInputType inputType, int inputMods) {
        if (keyName.equals("LMB") && inputType.equals(EInputType.RELEASE)) {
            modifyTile(parent, true);
        }

        if (keyName.equals("RMB") && inputType.equals(EInputType.RELEASE)) {
            modifyTile(parent, false);
        }

        if (keyName.equals("1" ) && inputType.equals(EInputType.RELEASE)) {
            moveSpeed+=50;
            if (moveSpeed > 1500) {
                moveSpeed = 1500;
            }
        }

        if (keyName.equals("2") && inputType.equals(EInputType.RELEASE)) {
            moveSpeed-=50;
            if (moveSpeed < 50) {
                moveSpeed = 50;
            }
        }

        if (keyName.equals("=") && inputType.equals(EInputType.RELEASE)) {
            Scene s = parent.getLinkedScene();
            double newScaleX = s.getScaleX() + 0.2d;
            double newScaleY = s.getScaleY() + 0.2d;
            if (newScaleX > 5 || newScaleY > 5) {
                newScaleX = 5;
                newScaleY = 5;
            }
            s.setScaleX(newScaleX);
            s.setScaleY(newScaleY);
        }
        else if (keyName.equals("-") && inputType.equals(EInputType.RELEASE)) {
            Scene s = parent.getLinkedScene();
            double newScaleX = s.getScaleX() - 0.2d;
            double newScaleY = s.getScaleY() - 0.2d;
            if (newScaleX < 0.2 || newScaleY < 0.2) {
                newScaleX = 0.2;
                newScaleY = 0.2;
            }
            s.setScaleX(newScaleX);
            s.setScaleY(newScaleY);
        }
    }
}
