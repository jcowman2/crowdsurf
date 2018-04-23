package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class IllegalCommandEvent extends GameEvent {
    String explanation

    IllegalCommandEvent(String exp) {
        explanation = exp
    }

    @Override
    protected void onExecute(GameInstance game) {
        //do nothing
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.error(explanation)]
    }
}
