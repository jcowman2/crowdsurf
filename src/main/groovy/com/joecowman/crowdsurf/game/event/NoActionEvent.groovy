package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class NoActionEvent extends GameEvent {

    @Override
    protected void onExecute(GameInstance game) {
        //do nothing
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.empty()]
    }
}
