package com.project.game.editor;

import com.project.engine.Core.Engine;
import com.project.engine.Core.GameObject;
import com.project.engine.Core.Scene;
import com.project.engine.Core.Tuple;
import com.project.engine.Core.Window.GameWindow;
import com.project.engine.Input.EInputType;
import com.project.engine.Scripting.IScriptable;
import com.project.game.TileCache;

public class CameraController implements IScriptable {

    float moveSpeed = 150f;

    @Override
    public void update(GameObject parent, double deltaTime) {
        GameWindow win = Engine.getInstance().getPrimaryWindow();
        if (win == null) {
            return;
        }

        if (win.isKeyPressed("A") || win.isKeyPressed("LEFT")) {
            parent.getTransform().translate(-moveSpeed*deltaTime, 0d);
        }
        if (win.isKeyPressed("D") || win.isKeyPressed("RIGHT")) {
            parent.getTransform().translate(moveSpeed*deltaTime, 0d);
        }
        if (win.isKeyPressed("W") || win.isKeyPressed("UP")) {
            parent.getTransform().translate(0d, -moveSpeed*deltaTime);
        }
        if (win.isKeyPressed("S") || win.isKeyPressed("DOWN")) {
            parent.getTransform().translate(0d, moveSpeed*deltaTime);
        }
    }

    private Tuple<Integer, Integer> getMousePos(GameObject parent, Scene s,GameWindow w) {
        int preX = w.getMouseX();
        int preY = w.getMouseY();

        // adjust for screen scaling
        preX /= (int) s.getScaleX();
        preY /= (int) s.getScaleY();

        preX += parent.getTransform().getPositionX();
        preY += parent.getTransform().getPositionY();

        int xPos = ((int)((preX/(0.0f + TileCache.BASE_TILE_SIZE)))) * 64;
        int yPos = ((int)((preY/(0.0f + TileCache.BASE_TILE_SIZE)))) * 64;

        return new Tuple<>(xPos, yPos);
    }

    private void modifyTile(GameObject parent, boolean add) {
        GameWindow w = Engine.getInstance().getPrimaryWindow();
        if (w == null) {
            return;
        }
        Scene s = parent.getLinkedScene();
        LevelEditor le = (LevelEditor) s;

        Tuple<Integer, Integer> mousePos = getMousePos(parent, s, w);

        if (add)
            le.addTile(le.selectedTileType, mousePos.getFirst(), mousePos.getSecond(), 1, 1);
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
