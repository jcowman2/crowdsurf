package com.joecowman.crowdsurf.api.model

import com.joecowman.crowdsurf.game.model.GameState

class GameRequest {
    String command
    GameState state
    Object payload
}
