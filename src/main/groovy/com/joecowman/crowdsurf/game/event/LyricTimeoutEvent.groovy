package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class LyricTimeoutEvent extends GameEvent {

    @Override
    protected void onExecute(GameInstance game) {
        game.enqueue(new SongDurationEvent())
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.normal("The crowd is getting restless. You should say something!")]
    }

}
