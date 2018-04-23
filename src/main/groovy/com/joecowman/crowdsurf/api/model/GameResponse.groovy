package com.joecowman.crowdsurf.api.model

import com.joecowman.crowdsurf.game.model.GameState

class GameResponse {
    List<OutputLine> output
    ResponseRequirements requirements = new ResponseRequirements()
    GameState state
}
