package com.project.use_cases.player_move;

import com.project.use_cases.core.game.GameInteractor;

import java.util.HashMap;

public class PlayerMoveInputData {
    private int mouseX;
    private int mouseY;
    private HashMap<String, Boolean> keys = new HashMap<>();
    PlayerMoveInputBoundary inputController;

    public PlayerMoveInputData() {}

    public void init() {
        inputController = new PlayerMoveInputBoundary() {};

        GameInteractor e = GameInteractor.getInstance();
        inputController.getInputController().addKeyboardListeners(e.getPrimaryWindow().getUnderlyingWindow(), () -> e.getPrimaryWindow().getActiveScene(), keys);
        inputController.getInputController().addMouseListeners(e.getPrimaryWindow().getGamePanel(), () -> e.getPrimaryWindow().getActiveScene(), keys);
        inputController.getInputController().addMouseMotionListeners(e.getPrimaryWindow().getGamePanel(), this::setMousePosition, this::setMousePosition);
    }

    public boolean isKeyPressed(String key) {
        return keys.getOrDefault(key.toUpperCase(), false);
    }

    public void resetInput() {
        keys.forEach((k, v) -> keys.put(k, false));
    }

    public int getMouseX() {
        return (int) (mouseX / GameInteractor.getInstance().getPrimaryWindow().getScaleFactorX());
    }

    public int getMouseY() {
        return (int) (mouseY / GameInteractor.getInstance().getPrimaryWindow().getScaleFactorY());
    }

    private void setMousePosition(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
}
