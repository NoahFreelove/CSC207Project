package com.project.use_cases.player_move;

import com.project.interface_adapters.player_move.PlayerMoveInputController;

public interface PlayerMoveInputBoundary {
    default PlayerMoveInputController getInputController() {

        return PlayerMoveInputController.getInstance();
    }


}
