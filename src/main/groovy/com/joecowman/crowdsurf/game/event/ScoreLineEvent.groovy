package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance


class ScoreLineEvent extends GameEvent {

    @Override
    protected void onExecute(GameInstance game) {

    }

    @Override
    protected List<OutputLine> generateOutput() {
        return null
    }
}
